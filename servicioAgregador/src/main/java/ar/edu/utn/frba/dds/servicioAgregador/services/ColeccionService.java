package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.PermisoCrearColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IColeccionRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IFuenteRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IUserRepository;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapColeccionOutput;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapHechoOutput;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Setter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ColeccionService implements IColeccionService{
  private final IColeccionRepository coleccionRepository;
  private final IFuenteRepository fuenteRepository;
  private final IFuenteProxyRepository fuenteEstaticaDinamicaRepository;
  private final IUserRepository userRepository;
  private final IHechoRepository hechoRepository;
  @Setter
  private MapColeccionOutput mapperColeccionOutput;
  @Setter
  private MapHechoOutput mapperHechoOutput;
  private FactoryAlgoritmo algoritmoFactory;

  public ColeccionService(IColeccionRepository coleccionRepository,
                          IUserRepository userRepository,
                          IHechoRepository hechoRepository,
                          IFuenteRepository fuenteRepository,
                          FactoryAlgoritmo algoritmoFactory) {
    this.coleccionRepository = coleccionRepository;
    this.userRepository = userRepository;
    this.hechoRepository = hechoRepository;
    this.fuenteRepository = fuenteRepository;
    this.algoritmoFactory = algoritmoFactory;
  }


  public void agregarConexionAFuente(Origen origenFuente, Fuente fuente) {
    this.fuenteRepository.save(origenFuente, fuente);
  }

  @Override
  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccionInput, String algoritmo) {
    Usuario usuarioSolicitante = this.userRepository.findById(coleccionInput.getUsuario());
    if(!usuarioSolicitante.tienePermisoDe(new PermisoCrearColeccion())) {
      throw new RuntimeException("Se debe tener permisos de administrador");
    }

    Coleccion coleccionCreada = new Coleccion(coleccionInput.getNombre(), coleccionInput.getDescripcion(), this.algoritmoFactory.getAlgoritmo(algoritmo));
    Set<Fuente> fuentes =  coleccionInput.getFuentes().stream().map(this::toFuente).collect(Collectors.toSet());
    coleccionCreada.agregarFuentes(fuentes);

    Coleccion coleccionGuardada = this.coleccionRepository.save(coleccionCreada);
    return this.mapperColeccionOutput.toColeccionDTOOutput(coleccionGuardada);
  }

  @Override
  public Mono<Void> actualizarHechosColecciones() {
    return Flux.fromIterable(this.coleccionRepository.findAll())
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
    return fuente.actualizarHechosFuente();
  }

  @Override
  public List<ColeccionDTOOutput> getAllColecciones(){
    Set<Coleccion> colecciones = this.coleccionRepository.findAll();
    return colecciones.stream().map(this.mapperColeccionOutput::toColeccionDTOOutput).collect(Collectors.toList());
  }

  @Override
  public ConjuntoHechoCompleto getHechosPorColeccion(String idColeccion, FiltroDTO filtro) {
    Coleccion coleccion = this.coleccionRepository.findById(idColeccion);
    coleccion.getFuentes().forEach(fuente -> this.cargarHechosEnFuente(fuente, filtro));
    List<Hecho> hechos = coleccion.getHechos().stream().filter(hecho -> !hecho.isEliminado()).toList();
    return this.mapperHechoOutput.toConjuntoHechoDTOOutput(hechos);
  }

  private void cargarHechosEnFuente(Fuente fuente, FiltroDTO filtro) {
    List<Hecho> hechos;
    if(!filtro.getEntiemporeal()) {
      hechos = this.hechoRepository.findByOrigen(fuente.getOrigen());
    } else  {
      hechos = fuente.getHechosEnTiempoReal(filtro);
    }
    fuente.actualizarHechos(hechos);
  }

  public Mono<Void> consensuarHechos() {
    Set<Coleccion> colecciones = this.coleccionRepository.findAll();
    List<Fuente> fuentes = this.fuenteRepository.findAll();
    return Flux.fromIterable(colecciones).flatMap(
        coleccion -> {
          coleccion.consensuarHechos(fuentes);
          return Mono.empty();
        }
    ).then();
  }

}
