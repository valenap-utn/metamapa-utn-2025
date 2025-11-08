package ar.edu.utn.frba.dds.services;


import ar.edu.utn.frba.dds.model.dtos.ColeccionDTO;
import ar.edu.utn.frba.dds.model.dtos.ConjuntoEstadisticasDTO;
import ar.edu.utn.frba.dds.model.entities.DatoCalculo;
import ar.edu.utn.frba.dds.model.entities.Hecho;
import ar.edu.utn.frba.dds.model.entities.Solicitud;
import ar.edu.utn.frba.dds.services.servicioAgregador.ServicioAgregador;
import ar.edu.utn.frba.dds.utils.Cache;
import ar.edu.utn.frba.dds.utils.CalculadorEstadisticas;
import ar.edu.utn.frba.dds.utils.CategoriaHoraMayorHechos;
import ar.edu.utn.frba.dds.utils.CategoriaMayorHechos;
import ar.edu.utn.frba.dds.utils.CategoriaProvinciaMayorHechos;
import ar.edu.utn.frba.dds.utils.ColeccionProvinciaMayorHechos;
import ar.edu.utn.frba.dds.utils.ExportadorCSV;
import ar.edu.utn.frba.dds.utils.SolicitudesSpamTotal;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ServicioEstadisticas {

    private final ServicioAgregador agregador;
    private final List<CalculadorEstadisticas> calculadores;
    private final Cache cache;
    private final ExportadorCSV exportador;

    public ServicioEstadisticas(ServicioAgregador agregador) {
        this.agregador = agregador;
        this.calculadores = List.of(new CategoriaHoraMayorHechos(), new CategoriaMayorHechos(), new CategoriaProvinciaMayorHechos(),
                new ColeccionProvinciaMayorHechos(), new SolicitudesSpamTotal());
        this.cache = new Cache();
        this.exportador = new ExportadorCSV();
    }

    public void recalcular() {

        List<Solicitud> solicitudes = agregador.obtenerSolicitudes();
        List<ColeccionDTO> colecciones = agregador.obtenerColecciones();
        List<Hecho> hechos = agregador.obtenerHechos();
        colecciones.forEach(coleccion -> hechos.stream().filter(
                hecho -> coleccion.getFuentes().stream().anyMatch(
                        fuenteDTO -> hecho.getOrigen().equals(fuenteDTO)
                )
        ).forEach(
                hecho -> hecho.agregarTituloColeccion(coleccion.getTitulo())
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

    public ConjuntoEstadisticasDTO obtenerEstadisticas() {
        return cache.obtener();
    }

    public void exportarCSV(Path destino) throws IOException {
        ConjuntoEstadisticasDTO data = cache.obtener();
        String csv = exportador.generarCSV(data);
        Files.writeString(destino, csv);
    }
}
