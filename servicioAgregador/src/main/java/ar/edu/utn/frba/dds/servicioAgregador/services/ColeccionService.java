package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.ColeccionConDatosErroneos;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.ColeccionNoEncontrada;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.ColeccionYaEliminada;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.UsuarioSinPermiso;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.VerificadorNormalizador;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.FuenteColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.Filtro;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.TipoOrigen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.Rol;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.ICategoriaRepositoryJPA;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.IColeccionRepositoryJPA;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.IHechoRepositoryJPA;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.IOrigenRepositoryJPA;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.IUserRepositoryJPA;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.specifications.HechoSpecification;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.ClientFuente;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapColeccionOutput;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapHechoOutput;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ColeccionService implements IColeccionService{
  private final IColeccionRepositoryJPA coleccionRepository;
  private final IUserRepositoryJPA userRepository;
  private final IHechoRepositoryJPA hechoRepository;
  @Setter
  private MapColeccionOutput mapperColeccionOutput;
  @Setter
  private MapHechoOutput mapperHechoOutput;
  private final FactoryAlgoritmo algoritmoFactory;
  private final FactoryClientFuente clientFuenteFactory;
  private final VerificadorNormalizador verificadorNormalizador;
  private final IOrigenRepositoryJPA origenRepository;
  private final ICategoriaRepositoryJPA categoriaRepository;
  private final ClientServicioUsuario clientServicioUsuario;
  @Value("${api.value.tamanio.pagina}")
  private Integer tamanioPagina;

  public ColeccionService(IColeccionRepositoryJPA coleccionRepository,
                          IUserRepositoryJPA userRepository,
                          IHechoRepositoryJPA hechoRepository,
                          FactoryAlgoritmo algoritmoFactory,
                          FactoryClientFuente clientFuenteFactory,
                          MapColeccionOutput mapperColeccionOutput,
                          MapHechoOutput mapperHechoOutput, VerificadorNormalizador verificadorNormalizador, IOrigenRepositoryJPA origenRepository, ICategoriaRepositoryJPA categoriaRepository, ClientServicioUsuario clientServicioUsuario) {
    this.coleccionRepository = coleccionRepository;
    this.userRepository = userRepository;
    this.hechoRepository = hechoRepository;
    this.algoritmoFactory = algoritmoFactory;
    this.clientFuenteFactory = clientFuenteFactory;
    this.mapperColeccionOutput = mapperColeccionOutput;
    this.mapperHechoOutput = mapperHechoOutput;
    this.verificadorNormalizador = verificadorNormalizador;
    this.origenRepository = origenRepository;
    this.categoriaRepository = categoriaRepository;
    this.clientServicioUsuario = clientServicioUsuario;
  }


  @Override
  @Transactional
  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccionInput) {
    if(coleccionInput.getIdUsuario() == null) {
      throw new UsuarioNoEncontrado("El usuario con el identificador administrado no existe");
    }
    Usuario usuarioSolicitante = this.getOrSaveUsuario(coleccionInput.getUsuario());

    if(!usuarioSolicitante.tienePermisoDe(Permiso.CREARCOLECCION, Rol.ADMINISTRADOR)) {
      throw new UsuarioSinPermiso("Se debe tener permisos de administrador");
    }

    Coleccion coleccionCreada = new Coleccion(coleccionInput.getTitulo(), coleccionInput.getDescripcion(), this.algoritmoFactory.getAlgoritmo(coleccionInput.getAlgoritmo()));
    if(coleccionInput.getFuentes() == null || coleccionInput.getFuentes().isEmpty()) {
      throw new ColeccionConDatosErroneos("Es necesario ingresar las fuentes para la colección");
    }

    Set<FuenteColeccion> fuenteColeccions =  coleccionInput.getFuentes().stream().map(this.mapperColeccionOutput::toFuente).collect(Collectors.toSet());
    coleccionCreada.agregarFuentes(fuenteColeccions);
    List<Filtro> filtros = coleccionInput.getCriterios().stream().map(this.mapperColeccionOutput::toCriterio).toList();
    coleccionCreada.agregarCriterios(filtros);

    fuenteColeccions.forEach(fuenteColeccion -> this.saveOrigenHechoNuevo(fuenteColeccion.getOrigen()));
    Coleccion coleccionGuardada = this.coleccionRepository.save(coleccionCreada);
    return this.mapperColeccionOutput.toColeccionDTOOutput(coleccionGuardada);
  }



  @Override
  @Transactional
  public Mono<Void> actualizarHechosColecciones() {
    return Flux.fromIterable(this.getOrigenesAActualizar())
                    .flatMap(this::actualizarHechosFuentes).then();
  }

  private List<Origen> getOrigenesAActualizar() {
    return this.origenRepository.findAllOrigenesFuente();
  }

  private Mono<Void> actualizarHechosFuentes(Origen origen) {
    return Mono.just(origen)
            .publishOn(Schedulers.boundedElastic())
            .flatMap(origen1 -> {
              List<Hecho> hechos = this.getHechosClient(origen, null);
              hechos.forEach(hecho -> hecho.setOrigen(this.saveOrigenHechoNuevo(hecho.getOrigen())));
              hechos.forEach( h -> this.verSiNormalizar(h));
              this.userRepository.saveAll(hechos.stream().map(Hecho::getUsuario).filter(Objects::nonNull).toList());
              this.categoriaRepository.saveAll(hechos.stream().map(Hecho::getCategoria).toList());
              this.hechoRepository.saveAll(hechos);
              return Mono.empty();
            }).then();
  }

  private Origen saveOrigenHechoNuevo(Origen origen) {
    List<Origen> origenes;
    Origen origenObtenido = null;
    if(origen.getTipo() == TipoOrigen.PROXY) {
      origenes = this.origenRepository.findByUrlAndTipoAndClientNombre(origen.getUrl(), origen.getTipo(), origen.getNombreAPI());
    }
    else{
       origenes = this.origenRepository.findByUrlAndTipo(origen.getUrl(), origen.getTipo());
    }
    if (!origenes.isEmpty()) {
      origenObtenido = origenes.get(0);
    }

    if(origenObtenido == null) {
      origenObtenido = this.origenRepository.save(origen);
    }
    return origenObtenido;
  }

  private void verSiNormalizar(Hecho hecho) {
    Hecho  hechoInterno =  this.hechoRepository.findByIdExternoAndOrigen(hecho.getIdExterno(), hecho.getIdOrigen());
    if(hechoInterno != null) {
        hecho.setId(hechoInterno.getId());
        if (this.verificadorNormalizador.estaNormalizado(hechoInterno, hecho)) {
          hecho.marcarComoNormalizado();
          hecho.setId(hechoInterno.getId());
          hecho.setTitulo(hechoInterno.getTitulo());
          hecho.setUbicacion(hechoInterno.getUbicacion());
          hecho.setCategoria(hechoInterno.getCategoria());
        }
    }
  }

  @Override
  @Transactional
  public List<ColeccionDTOOutput> getAllColecciones(){
    List<Coleccion> colecciones = this.coleccionRepository.findAllByEliminada(Boolean.FALSE);
    return colecciones.stream().map(this.mapperColeccionOutput::toColeccionDTOOutput).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ConjuntoHechoCompleto getHechosPorColeccion(UUID idColeccion, FiltroDTO filtro) {
    Coleccion coleccion = this.coleccionRepository.findById(idColeccion).orElse(null);
    if (coleccion == null) {
      throw  new ColeccionNoEncontrada("No se ha encontrado la coleccion de id " + idColeccion);
    }
    coleccion.getFuenteColeccions().forEach(fuente -> this.cargarHechosEnFuente(fuente, filtro));
    List<Hecho> hechos = coleccion.getHechos();
    if(filtro.getCurada()){
      hechos = hechos.stream().filter(hecho -> hecho.estaCuradoPor(coleccion.getAlgoritmoConsenso())).toList();
    }
    List<Categoria> categorias = this.categoriaRepository.findAll(PageRequest.of(0, this.tamanioPagina)).getContent();
    return this.mapperHechoOutput.toConjuntoHechoDTOOutput(hechos, categorias);
  }

  @Override
  @Transactional
  public ColeccionDTOOutput cambiarColeccion(ColeccionDTOInput coleccionInput, UUID idColeccion) {
    if(coleccionInput.getIdUsuario() == null) {
      throw new UsuarioNoEncontrado("El usuario con el identificador administrado no existe");
    }
    Usuario usuarioSolicitante = this.getOrSaveUsuario(coleccionInput.getUsuario());

    if(!usuarioSolicitante.tienePermisoDe(Permiso.MODIFICARCOLECCION, Rol.ADMINISTRADOR)) {
      throw new UsuarioSinPermiso("Se debe tener permisos de administrador");
    }

    Coleccion coleccion = this.coleccionRepository.findById(idColeccion).orElse(null);
    if(coleccion == null) {
      throw new ColeccionNoEncontrada("No existe la coleccion con el identificador enviado");
    }
    coleccion.setDescripcion(coleccionInput.getDescripcion());
    coleccion.setTitulo(coleccionInput.getTitulo());
    List<FuenteColeccion> fuenteColeccions = coleccionInput.getFuentes().stream().map(this.mapperColeccionOutput::toFuente).toList();
    coleccion.actualizarFuentes(fuenteColeccions);
    List<Filtro> filtros = coleccionInput.getCriterios().stream().map(this.mapperColeccionOutput::toCriterio).toList();
    coleccion.actualizarCriterios(filtros);
    coleccion.setAlgoritmoConsenso(this.algoritmoFactory.getAlgoritmo(coleccionInput.getAlgoritmo()));
    fuenteColeccions.forEach(fuenteColeccion -> this.saveOrigenHechoNuevo(fuenteColeccion.getOrigen()));
    Coleccion coleccionGuardada = this.coleccionRepository.save(coleccion);
    return this.mapperColeccionOutput.toColeccionDTOOutput(coleccionGuardada);
  }

  @Transactional
  @Override
  public ColeccionDTOOutput eliminarColeccion(UUID id, Long idUsuario) {
    UsuarioDTO usuariodto = this.clientServicioUsuario.findUsuarioById(idUsuario);

    if( usuariodto.getId() == null) {
      throw new UsuarioNoEncontrado("El usuario con el identificador administrado no existe");
    }
    Usuario usuario = this.getOrSaveUsuario(usuariodto);
    if (!usuario.tienePermisoDe(Permiso.ELIMINARCOLECCION, Rol.ADMINISTRADOR)) {
      throw new UsuarioSinPermiso("El usuario no tiene permiso para eliminar la colección");
    }
    Coleccion coleccion = this.coleccionRepository.findById(id).orElse(null);
    if (coleccion == null) {
      throw new ColeccionNoEncontrada("La colección de id " + id + " no existe");
    }
    if (coleccion.getEliminada() == true) {
      throw new ColeccionYaEliminada("La coleccion de id " + id + "ya está eliminada");
    }
    coleccion.marcarComoEliminada();
    return this.mapperColeccionOutput.toColeccionDTOOutput(coleccion);
  }

  @Override
  public ColeccionDTOOutput findColeccionById(UUID id) {
    Coleccion coleccion = this.coleccionRepository.findById(id).orElseThrow(() -> new ColeccionNoEncontrada("La colección de id: " + id + " no existe"));
    return this.mapperColeccionOutput.toColeccionDTOOutput(coleccion);
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
      if (filtro.getNroPagina() == null || filtro.getNroPagina() < 0) {
        filtro.setNroPagina(0);
      }
      PageRequest pageable = PageRequest.of(filtro.getNroPagina(), this.tamanioPagina);
      Specification<Hecho> especificacionFiltros = HechoSpecification.filterBy(filtro, fuenteColeccion.getOrigen());
      hechos.addAll(this.hechoRepository.findAll(especificacionFiltros, pageable).getContent());

    }
    if(filtro.tieneFiltroUbicacion()) {
      hechos = filtro.filtrarPorUbicacion(hechos, this.mapperColeccionOutput.getCantidadAceptableUbicacion());
    }
    fuenteColeccion.actualizarHechos(hechos);
  }

  public Mono<Void> consensuarHechos() {
    List<Coleccion> colecciones = this.coleccionRepository.findAll();
    Set<FuenteColeccion> fuenteColeccions = new HashSet<>();
    colecciones.forEach(coleccion -> fuenteColeccions.addAll(coleccion.getFuenteColeccions()));
    fuenteColeccions.forEach(fuente -> fuente.actualizarHechos(this.hechoRepository.findByOrigen(fuente.getOrigen())));
    return Flux.fromIterable(colecciones).flatMap(
        coleccion -> {
          coleccion.consensuarHechos(fuenteColeccions);
          return Mono.just(coleccion);
        }
    ).flatMap(coleccion -> {
      this.hechoRepository.saveAll(
              fuenteColeccions.stream().flatMap( fuente -> fuente.getHechos().stream()).toList()
      );
      return Mono.empty();
    }).then();
  }

  private Usuario getOrSaveUsuario(UsuarioDTO usuarioDTO) {
    Usuario usuario = this.userRepository.findById(usuarioDTO.getId()).orElse(
            this.userRepository.save(Usuario.builder().id(usuarioDTO.getId()).email(usuarioDTO.getEmail())
                    .nombre(usuarioDTO.getNombre()).apellido(usuarioDTO.getApellido()).build())
    );
    usuario.cargarRolYPermisos(usuarioDTO.getRol(), usuarioDTO.getPermisos());
    return usuario;
  }
}
