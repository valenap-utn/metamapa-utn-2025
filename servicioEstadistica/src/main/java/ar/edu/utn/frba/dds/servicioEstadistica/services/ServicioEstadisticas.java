package ar.edu.utn.frba.dds.servicioEstadistica.services;


import ar.edu.utn.frba.dds.servicioEstadistica.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioEstadistica.exceptions.UsuarioSinPermiso;
import ar.edu.utn.frba.dds.servicioEstadistica.model.dtos.ColeccionDTO;
import ar.edu.utn.frba.dds.servicioEstadistica.model.dtos.ConjuntoEstadisticasDTO;
import ar.edu.utn.frba.dds.servicioEstadistica.model.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.DatoCalculo;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.roles.Rol;
import ar.edu.utn.frba.dds.servicioEstadistica.services.servicioAgregador.ServicioAgregador;
import ar.edu.utn.frba.dds.servicioEstadistica.utils.Cache;
import ar.edu.utn.frba.dds.servicioEstadistica.utils.CalculadorEstadisticas;
import ar.edu.utn.frba.dds.servicioEstadistica.utils.CategoriaHoraMayorHechos;
import ar.edu.utn.frba.dds.servicioEstadistica.utils.CategoriaMayorHechos;
import ar.edu.utn.frba.dds.servicioEstadistica.utils.CategoriaProvinciaMayorHechos;
import ar.edu.utn.frba.dds.servicioEstadistica.utils.ColeccionProvinciaMayorHechos;
import ar.edu.utn.frba.dds.servicioEstadistica.utils.ExportadorCSV;
import ar.edu.utn.frba.dds.servicioEstadistica.utils.SolicitudesSpamTotal;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ServicioEstadisticas {

    private final ServicioAgregador agregador;
    private final List<CalculadorEstadisticas> calculadores;
    private final Cache cache;
    private final ExportadorCSV exportador;
    private final ClientServicioUsuario servicioUsuario;

    public ServicioEstadisticas(ServicioAgregador agregador, ClientServicioUsuario servicioUsuario) {
        this.agregador = agregador;
      this.servicioUsuario = servicioUsuario;
      this.calculadores = List.of(new CategoriaHoraMayorHechos(), new CategoriaMayorHechos(), new CategoriaProvinciaMayorHechos(),
                new ColeccionProvinciaMayorHechos(), new SolicitudesSpamTotal());
        this.cache = new Cache();
        this.exportador = new ExportadorCSV();
    }

    public void recalcular() {

        List<Solicitud> solicitudes = agregador.obtenerSolicitudes();
        List<ColeccionDTO> colecciones = agregador.obtenerColecciones();
        List<Hecho> hechos = agregador.obtenerHechos();
        colecciones.forEach(coleccion ->
            hechos.stream().
                filter(hecho -> coleccion.getFuentes().stream()
                    .anyMatch(fuenteDTO -> hecho.getOrigen().equals(fuenteDTO)))
                .forEach(hecho -> hecho.agregarTituloColeccion(coleccion.getTitulo())
        ));
        ConjuntoEstadisticasDTO conjuntoEstadisticasDTO = new ConjuntoEstadisticasDTO();
        DatoCalculo datosNecesarios = new DatoCalculo();
        datosNecesarios.setHechos(hechos);
        datosNecesarios.setSolicitudes(solicitudes);
        datosNecesarios.setTitulosColeccion(colecciones.stream().map(ColeccionDTO::getTitulo).toList());

        this.calculadores.forEach( calculadorEstadisticas ->
                conjuntoEstadisticasDTO.agregarEstadistica(calculadorEstadisticas.calcular(datosNecesarios))
                );
        cache.actualizar(conjuntoEstadisticasDTO);
    }

    public ConjuntoEstadisticasDTO obtenerEstadisticas(Long idUsuario) {
        this.verificarUsuario(idUsuario);
        return cache.obtener();
    }

    /**
     * Para el controller que expone /estadisticas.csv
     * Devuelve un stream de bytes en memoria listo para mandar por HTTP.
     */
    public ByteArrayInputStream exportarCSV(Long idUsuario){
        this.verificarUsuario(idUsuario);
        ConjuntoEstadisticasDTO data = cache.obtener();
        String csv = exportador.generarCSV(data);
        return new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));
    }

    private void verificarUsuario(Long idUsuario) {
        UsuarioDTO usuarioDTO = this.servicioUsuario.findUsuarioById(idUsuario);

        if (usuarioDTO.getId() == null) {
            throw new UsuarioNoEncontrado("Usuario no encontrado");
        }
        Usuario usuario = Usuario.builder().rol(usuarioDTO.getRol()).id(usuarioDTO.getId())
                .email(usuarioDTO.getEmail()).permisos(usuarioDTO.getPermisos())
                .nombre(usuarioDTO.getNombre()).apellido(usuarioDTO.getApellido()).build();

        if (!usuario.tienePermisoDe(Permiso.VISITARESTADISTICAS, Rol.ADMINISTRADOR)) {
            throw new UsuarioSinPermiso("El usuario no tiene los permisos suficientes");
        }
    }

    /**
     * Si queremos mantener que escribe en disco para uso interno/cron
     */
    public void exportarCSVEnArchivo(Path destino, Long idUsuario) throws IOException {
        this.verificarUsuario(idUsuario);
        ConjuntoEstadisticasDTO data = cache.obtener();
        String csv = exportador.generarCSV(data);
        Files.writeString(destino, csv);
    }
}
