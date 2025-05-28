package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoProxy;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.FuenteDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTOCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.PermisoCrearColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IColeccionRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IUserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ColeccionService implements IColeccionService{
  private final IColeccionRepository coleccionRepository;
  private final Map<Long, ConexionFuenteService> conexionFuentes;
  private final IUserRepository userRepository;
  private final IHechoRepository hechoRepository;

  public ColeccionService(IColeccionRepository coleccionRepository,
                          IUserRepository userRepository,
                          IHechoRepository hechoRepository) {
    this.coleccionRepository = coleccionRepository;
    this.conexionFuentes = new HashMap<>();
    this.userRepository = userRepository;
    this.hechoRepository = hechoRepository;
  }

  public void agregarConexionAFuente(Long idFuente, ConexionFuenteService conexionFuente) {
    this.conexionFuentes.put(idFuente, conexionFuente);
  }

  @Override
  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccionInput) {
    Usuario usuarioSolicitante = this.userRepository.findById(coleccionInput.getUsuario());
    if(!usuarioSolicitante.tienePermisoDe(new PermisoCrearColeccion())) {
      throw new RuntimeException("Se debe tener permisos de administrador");
    }

    Coleccion coleccionCreada = new Coleccion(coleccionInput.getNombre(), coleccionInput.getDescripcion());
    Set<Fuente> fuentes =  coleccionInput.getFuentes().stream().map(this::toFuente).collect(Collectors.toSet());
    fuentes.forEach(this.hechoRepository::saveHechosDeFuente);
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
  public Mono<Void> actualizarHechosColecciones() {
    Set<Coleccion> colecciones = this.coleccionRepository.findAll();
    return Flux.fromIterable(colecciones)
                    .flatMap(coleccion -> {
                      List<Fuente> fuentes = coleccion.getFuentes();
                      return this.actualizarHechosFuentes(fuentes);
                    }).then();
  }

  private Mono<Void> actualizarHechosFuentes(List<Fuente> fuentes) {
    return Flux.fromIterable(fuentes)
            .flatMap(this::actualizarHechosPorFuente).then();
  }

  private Mono<Void> actualizarHechosPorFuente(Fuente fuente) {
    ConexionFuenteService conexionFuenteService = this.conexionFuentes.get(fuente.getId());
    return conexionFuenteService.actualizarHechosFuente(fuente, this.hechoRepository);
  }

  @Override
  public List<ColeccionDTOOutput> getAllColecciones(){
    Set<Coleccion> colecciones = this.coleccionRepository.findAll();
    return colecciones.stream().map(this::toColeccionDTOOutput).collect(Collectors.toList());
  }

  @Override
  public ConjuntoHechoProxy getHechosPorColeccion(String idColeccion) {
    Coleccion coleccion = this.coleccionRepository.findById(idColeccion);
    coleccion.getFuentes().forEach(this::cargarHechosEnFuente);
    Set<Hecho> hechos = coleccion.getHechos();
    return this.toConjuntoHechoDTO(hechos);
  }

  private ConjuntoHechoProxy toConjuntoHechoDTO(Set<Hecho> hechos) {
    ConjuntoHechoProxy conjuntoDeHechos = new ConjuntoHechoProxy();
    Set<HechoDTOCompleto> hechosDTO =  hechos.stream().map(this::toHechoDTO).collect(Collectors.toSet());
    conjuntoDeHechos.setHechos(hechosDTO);
    return conjuntoDeHechos;
  }

  private HechoDTOCompleto toHechoDTO(Hecho hecho) {
    HechoDTOCompleto hechoDTO = new HechoDTOCompleto();
    hechoDTO.setId(hecho.getId());
    return hechoDTO;
  }

  private void cargarHechosEnFuente(Fuente fuente) {
   ConexionFuenteService conexion =  this.conexionFuentes.get(fuente.getId());
   conexion.cargarHechosEnFuente(fuente, this.hechoRepository);
  }

}
