package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.FuenteDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoValueObject;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.PermisoCrearColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IColeccionRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IUserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service

public class ColeccionService implements IColeccionService{
  private final IColeccionRepository coleccionRepository;
  private Map<Long, ConexionFuenteService> conexionFuentes;
  private final IUserRepository userRepository;
  private final IHechoRepository hechoRepository;

  public ColeccionService(IColeccionRepository coleccionRepository, IUserRepository userRepository, IHechoRepository hechoRepository) {
    this.coleccionRepository = coleccionRepository;
    this.conexionFuentes = new HashMap<>();
    this.userRepository = userRepository;
    this.hechoRepository = hechoRepository;
  }

  @Override
  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccionInput) {
    Usuario usuarioSolicitante = this.userRepository.findById(coleccionInput.getUsuario());
    if(!usuarioSolicitante.tienePermisoDe(new PermisoCrearColeccion())) {
      //throw new Exception("Se debe tener permisos de administrador");
    }

    Coleccion coleccionCreada = new Coleccion(coleccionInput.getNombre(), coleccionInput.getDescripcion());
    Set<Fuente> fuentes =  coleccionInput.getFuentes().stream().map(this::toFuente).collect(Collectors.toSet());
    coleccionCreada.agregarFuentes(fuentes);

    Coleccion coleccionGuardada = this.coleccionRepository.save(coleccionCreada);
    return this.toColeccionDTOOutput(coleccionGuardada);
  }

  private ColeccionDTOOutput toColeccionDTOOutput(Coleccion coleccion) {
    ColeccionDTOOutput coleccionDTOOutput = new ColeccionDTOOutput();
    coleccionDTOOutput.setId(coleccion.getId());
    coleccionDTOOutput.setNombre(coleccion.getDescripcion());
    coleccionDTOOutput.setDescripcion(coleccion.getDescripcion());
    coleccionDTOOutput.setFuentes(coleccion.getFuentes().stream().map(this::toFuenteDTO).collect(Collectors.toList()));
    return coleccionDTOOutput;
  }

  private Fuente toFuente(FuenteDTO fuenteInput) {
    return new Fuente(fuenteInput.getId(), fuenteInput.getTipo());
  }

  private FuenteDTO toFuenteDTO(Fuente fuente) {
    FuenteDTO fuenteDTO = new FuenteDTO();
    fuenteDTO.setId(fuente.getId());
    fuenteDTO.setTipo(fuente.getTipo());
    return fuenteDTO;
  }

  @Override
  public void actualizarHechosColecciones() {
    Set<Coleccion> colecciones = this.coleccionRepository.findAll();
    colecciones.forEach(this::actualizarHechosColeccion);
  }

  private void actualizarHechosColeccion(Coleccion coleccion) {
    List<Fuente> fuentes = coleccion.getFuentes();
    Flux.fromIterable(fuentes)
            .flatMap(this::actualizarHechosFuente)
            .flatMap(fuente -> {
              this.hechoRepository.save(fuente);
              return Mono.empty();
            });
  }

  private Mono<Fuente> actualizarHechosFuente(Fuente fuente) {
    ConexionFuenteService conexionFuenteService = this.conexionFuentes.get(fuente.getId());
    return conexionFuenteService.actualizarHechosFuente(fuente);
  }

  @Override
  public List<ColeccionDTOOutput> getAllColecciones(){
    Set<Coleccion> colecciones = this.coleccionRepository.findAll();
    return colecciones.stream().map(this::toColeccionDTOOutput).collect(Collectors.toList());
  }

  @Override
  public List<HechoValueObject> getHechosPorColeccion(String idColeccion) {
    Coleccion coleccion = this.coleccionRepository.findById(idColeccion);
    coleccion.getFuentes().forEach(fuente -> this.getHechosFuente(fuente));
    Set<Hecho> hechos = coleccion.getHechos();
    return this.toConjuntoHechoDTO(hechos);
  }

  private void getHechoFuente(Fuente fuente) {
   ConexionFuenteService conexion =  this.conexionFuentes.get(fuente.getId());
   conexion.getHechosFuente(fuente);
  }

}
