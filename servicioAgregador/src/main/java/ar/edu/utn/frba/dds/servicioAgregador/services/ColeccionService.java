package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.ColeccionNoEncontrada;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.UsuarioSinPermiso;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.fuente.FuenteColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.Filtro;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.TipoOrigen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.PermisoCrearColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.PermisoModificarColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IColeccionRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechosExternosRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IUserRepository;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.ClientFuente;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapColeccionOutput;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapHechoOutput;
import java.util.ArrayList;
import java.util.HashSet;
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
  private final IUserRepository userRepository;
  private final IHechoRepository hechoRepository;
  @Setter
  private MapColeccionOutput mapperColeccionOutput;
  @Setter
  private MapHechoOutput mapperHechoOutput;
  private final FactoryAlgoritmo algoritmoFactory;
  private final FactoryClientFuente clientFuenteFactory;
  private final IHechosExternosRepository hechosExternosRepository;
  private final VerificadorNormalizador verificadorNormalizador;

  public ColeccionService(IColeccionRepository coleccionRepository,
                          IUserRepository userRepository,
                          IHechoRepository hechoRepository,
                          FactoryAlgoritmo algoritmoFactory,
                          FactoryClientFuente clientFuenteFactory,
                          IHechosExternosRepository hechosExternosRepository,
                          MapColeccionOutput mapperColeccionOutput,
                          MapHechoOutput mapperHechoOutput, VerificadorNormalizador verificadorNormalizador) {
    this.coleccionRepository = coleccionRepository;
    this.userRepository = userRepository;
    this.hechoRepository = hechoRepository;
    this.algoritmoFactory = algoritmoFactory;
    this.clientFuenteFactory = clientFuenteFactory;
    this.hechosExternosRepository = hechosExternosRepository;
    this.mapperColeccionOutput = mapperColeccionOutput;
    this.mapperHechoOutput = mapperHechoOutput;
    this.verificadorNormalizador = verificadorNormalizador;
  }


  @Override
  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccionInput) {
    Usuario usuarioSolicitante = this.userRepository.findById(coleccionInput.getUsuario());
    if(usuarioSolicitante == null) {
      throw new UsuarioNoEncontrado("El usuario con el identificador administrado no existe");
    }

    if(!usuarioSolicitante.tienePermisoDe(new PermisoCrearColeccion())) {
      throw new UsuarioSinPermiso("Se debe tener permisos de administrador");
    }

    Coleccion coleccionCreada = new Coleccion(coleccionInput.getNombre(), coleccionInput.getDescripcion(), this.algoritmoFactory.getAlgoritmo(coleccionInput.getAlgoritmo()));
    Set<FuenteColeccion> fuenteColeccions =  coleccionInput.getFuentes().stream().map(this.mapperColeccionOutput::toFuente).collect(Collectors.toSet());
    coleccionCreada.agregarFuentes(fuenteColeccions);
    List<Filtro> filtros = coleccionInput.getCriterios().stream().map(this.mapperColeccionOutput::toCriterio).toList();
    coleccionCreada.agregarCriterios(filtros);

    Coleccion coleccionGuardada = this.coleccionRepository.save(coleccionCreada);
    return this.mapperColeccionOutput.toColeccionDTOOutput(coleccionGuardada);
  }

  @Override
  public Mono<Void> actualizarHechosColecciones() {
    return Flux.fromIterable(this.coleccionRepository.findAll())
                    .flatMap(coleccion -> {
                      List<FuenteColeccion> fuenteColeccions = coleccion.getFuenteColeccions();
                      return this.actualizarHechosFuentes(fuenteColeccions);
                    }).then();
  }

  private Mono<Void> actualizarHechosFuentes(List<FuenteColeccion> fuenteColeccions) {
    return Flux.fromIterable(fuenteColeccions)
            .flatMap(fuente -> {
              List<Hecho> hechos = this.getHechosClient(fuente.getOrigen(), null);
              hechos = hechos.stream().map(this.hechoRepository::saveHecho).toList();
              hechos.forEach(this::verSiNormalizar);
              hechos.forEach(this.hechosExternosRepository::save);
              return Mono.empty();
            }).then();
  }

  private void verSiNormalizar(Hecho hecho) {
    Long idInterno =  this.hechosExternosRepository.findByIDExterno(hecho.getId());
    if(idInterno != null) {
      Hecho hechoExterno = this.hechoRepository.findById(idInterno);
      if(this.verificadorNormalizador.estaNormalizado(hecho, hechoExterno)) {
        hecho.marcarComoNormalizado();
      }
    }
  }

  @Override
  public List<ColeccionDTOOutput> getAllColecciones(){
    Set<Coleccion> colecciones = this.coleccionRepository.findAll();
    return colecciones.stream().map(this.mapperColeccionOutput::toColeccionDTOOutput).collect(Collectors.toList());
  }

  @Override
  public ConjuntoHechoCompleto getHechosPorColeccion(String idColeccion, FiltroDTO filtro) {
    Coleccion coleccion = this.coleccionRepository.findById(idColeccion);
    coleccion.getFuenteColeccions().forEach(fuente -> this.cargarHechosEnFuente(fuente, filtro));
    List<Hecho> hechos = coleccion.getHechos();
    if(filtro.getCurada()){
      hechos = hechos.stream().filter(hecho -> hecho.estaCuradoPor(coleccion.getAlgoritmoConsenso())).toList();
    }
    return this.mapperHechoOutput.toConjuntoHechoDTOOutput(hechos);
  }

  @Override
  public ColeccionDTOOutput cambiarColeccion(ColeccionDTOInput coleccionInput, String idColeccion) {
    Usuario usuarioSolicitante = this.userRepository.findById(coleccionInput.getUsuario());
    if(usuarioSolicitante == null) {
      throw new UsuarioNoEncontrado("El usuario con el identificador administrado no existe");
    }

    if(!usuarioSolicitante.tienePermisoDe(new PermisoModificarColeccion())) {
      throw new UsuarioSinPermiso("Se debe tener permisos de administrador");
    }

    Coleccion coleccion = this.coleccionRepository.findById(idColeccion);
    if(coleccion == null) {
      throw new ColeccionNoEncontrada("No existe la coleccion con el identificador enviado");
    }
    coleccion.setDescripcion(coleccionInput.getDescripcion());
    coleccion.setTitulo(coleccionInput.getNombre());
    List<FuenteColeccion> fuenteColeccions = coleccionInput.getFuentes().stream().map(this.mapperColeccionOutput::toFuente).toList();
    coleccion.actualizarFuentes(fuenteColeccions);
    List<Filtro> filtros = coleccionInput.getCriterios().stream().map(this.mapperColeccionOutput::toCriterio).toList();
    coleccion.actualizarCriterios(filtros);
    coleccion.setAlgoritmoConsenso(this.algoritmoFactory.getAlgoritmo(coleccionInput.getAlgoritmo()));
    Coleccion coleccionGuardada = this.coleccionRepository.save(coleccion);
    return this.mapperColeccionOutput.toColeccionDTOOutput(coleccionGuardada);
  }

  @Override
  public ColeccionDTOOutput eliminarColeccion(String id) {
    return null;
  }


  private List<Hecho> getHechosClient(Origen origen, FiltroDTO filtro) {
    ClientFuente client = this.clientFuenteFactory.getClientPorOrigen(origen);
    return client.getHechos(filtro);
  }

  private void cargarHechosEnFuente(FuenteColeccion fuenteColeccion, FiltroDTO filtro) {
    List<Hecho> hechos = new ArrayList<>();
    if(filtro.getEntiemporeal() &&  fuenteColeccion.getOrigen().getTipo() == TipoOrigen.PROXY) {
      hechos.addAll(this.getHechosClient(fuenteColeccion.getOrigen(), filtro));
    } else {
      hechos.addAll(this.hechoRepository.findByOrigenWithFiltros(fuenteColeccion.getOrigen(), filtro));
    }
    fuenteColeccion.actualizarHechos(hechos);
  }

  public Mono<Void> consensuarHechos() {
    Set<Coleccion> colecciones = this.coleccionRepository.findAll();
    Set<FuenteColeccion> fuenteColeccions = new HashSet<>();
    colecciones.forEach(coleccion -> fuenteColeccions.addAll(coleccion.getFuenteColeccions()));
    fuenteColeccions.forEach(fuente -> fuente.actualizarHechos(this.hechoRepository.findByOrigen(fuente.getOrigen())));
    return Flux.fromIterable(colecciones).flatMap(
        coleccion -> {
          coleccion.consensuarHechos(fuenteColeccions);
          return Mono.empty();
        }
    ).then();
  }

}
