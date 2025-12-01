package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.ErrorAPIGobierno;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Direccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.HechoParaNormalizar;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.normalizacion.IBuscadorFullTextSearch;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.IDireccionRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implEspecifica.ICategoriaRepositoryFullTextSearch;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implEspecifica.IDireccionRepositoryFullTextSearch;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implEspecifica.IHechoRepositoryFullTextSearch;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.ICategoriaRepositoryJPA;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.IHechoRepositoryJPA;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.ClientAPIGobierno;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class Estandarizador implements IEstandarizador {
  private final IHechoRepositoryFullTextSearch hechoRepositoryFTS;
  private final IHechoRepositoryJPA hechoRepository;
  private final ICategoriaRepositoryFullTextSearch categoriaRepositoryFTS;
  private final ICategoriaRepositoryJPA categoriaRepository;
  private final IBuscadorFullTextSearch buscadorFullTextSearch;
  private final IDireccionRepositoryFullTextSearch direccionRepositoryFTS;
  private final IDireccionRepository direccionRepository;
  private final ClientAPIGobierno apiGobierno;

  public Estandarizador(IHechoRepositoryFullTextSearch hechoRepositoryFTS, IHechoRepositoryJPA hechoRepository,
                        ICategoriaRepositoryFullTextSearch categoriaRepositoryFTS, ICategoriaRepositoryJPA categoriaRepository,
                        IBuscadorFullTextSearch buscadorFullTextSearch, IDireccionRepositoryFullTextSearch direccionRepositoryFTS,
                        IDireccionRepository direccionRepository, ClientAPIGobierno apiGobierno) {
    this.hechoRepositoryFTS = hechoRepositoryFTS;
    this.hechoRepository = hechoRepository;
    this.categoriaRepositoryFTS = categoriaRepositoryFTS;
    this.categoriaRepository = categoriaRepository;
    this.buscadorFullTextSearch = buscadorFullTextSearch;
    this.direccionRepositoryFTS = direccionRepositoryFTS;
    this.direccionRepository = direccionRepository;
    this.apiGobierno = apiGobierno;
  }
  @Override
  @Transactional
  public Mono<Void> estandarizarHechos() {
    return Flux.fromIterable(this.prepararHechos())
            .flatMap(hechoParaNormalizar -> this.estandarizarHecho(hechoParaNormalizar.getHecho(), hechoParaNormalizar.getHechosCopia()))
            .reduce((h1, h2) -> h2)
            .map(
              hechos -> {
                this.hechoRepository.saveAll(hechos);
                return Mono.empty();
              }
            )
            .subscribeOn(Schedulers.boundedElastic())
            .then();
  }
  public List<HechoParaNormalizar> prepararHechos() {
    List<Hecho> hechos = this.hechoRepository.findByNormalizado(PageRequest.of(0, 50), false);
    List<Hecho> hechosCopia = new ArrayList<>(hechos);
    return hechos.stream().map(hecho -> new HechoParaNormalizar(hecho, hechosCopia)).toList();
  }

  private Mono<List<Hecho>> estandarizarHecho(Hecho hecho, List<Hecho> hechosDesnormalizados) {
    return Mono.just(hecho).map(hechoAmodificar ->
            {
              this.estandarizarTitulo(hechoAmodificar, hechosDesnormalizados);
              this.estandarizarCategoria(hechoAmodificar, hechosDesnormalizados);
              this.estandarizarUbicacion(hechoAmodificar, hechosDesnormalizados);
              hechoAmodificar.marcarComoNormalizado();
              return hechosDesnormalizados;
            }
            );
  }

  private void estandarizarCategoria(Hecho hechoAmodificar, List<Hecho> hechosDesnormalizados) {
    List<Categoria> categorias = this.categoriaRepositoryFTS.findByFullTextSearch(hechoAmodificar.getNombreCategoria());
    Categoria categoria = categorias.stream().findFirst().orElse(null);
    if (categoria == null) {
      categoria = new Categoria();
      List<String> categoriasHechos =  hechosDesnormalizados.stream().map(Hecho::getNombreCategoria).toList();
      categoria.setNombre(this.buscadorFullTextSearch.crearNombreNormalizadoCon(hechoAmodificar.getNombreCategoria(), categoriasHechos));
      categoria.marcarNormalizada();
      this.categoriaRepository.save(categoria);
    }
    hechoAmodificar.setCategoria(categoria);
  }

  private void estandarizarTitulo(Hecho hechoAmodificar, List<Hecho> hechosDesnormalizados) {
    List<String> hechos = this.hechoRepositoryFTS.findByFullTextSearch(hechoAmodificar.getTitulo());
    String titulo = hechos.stream().findFirst().orElse(null);
    if (titulo == null) {
      List<String> titulos =  hechosDesnormalizados.stream().map(Hecho::getTitulo).toList();
      titulo = this.buscadorFullTextSearch.crearNombreNormalizadoCon(hechoAmodificar.getTitulo(), titulos);
    }
    hechoAmodificar.setTitulo(titulo);
  }

  private void estandarizarUbicacion(Hecho hechoAmodificar, List<Hecho> hechosDesnormalizados) {
    Ubicacion ubicacion =hechoAmodificar.getUbicacion();
    if (ubicacion == null || (ubicacion.getDireccion() == null && ubicacion.esNula())) {
      return;
    } else if (ubicacion.getDireccion() == null) {
      try {
        Direccion direccion = this.apiGobierno.buscarUbicacion(hechoAmodificar.getUbicacion());
        direccion.marcarNormalizada();
        ubicacion.setDireccion(direccion);
      } catch (Exception e) {
        System.out.println("Error en el API Gobierno: " + e.getMessage());
        return;
      }
    }

    List<Direccion> direcciones = this.direccionRepositoryFTS.findByFullTextSearch(hechoAmodificar.getDireccion());
    Direccion direccion = direcciones.stream().findFirst().orElse(null);
    if (direccion == null && !ubicacion.estaNormalizada()) {
      direccion = new Direccion();
      List<Direccion> direccionesHechos =  hechosDesnormalizados.stream().map(Hecho::getDireccion).toList();
      direccion.setProvincia(this.buscadorFullTextSearch.crearNombreNormalizadoCon(hechoAmodificar.getDireccion().getDepartamento(),
              direccionesHechos.stream().map(Direccion::getDepartamento).toList()));
      direccion.setProvincia(this.buscadorFullTextSearch.crearNombreNormalizadoCon(hechoAmodificar.getDireccion().getProvincia(),
              direccionesHechos.stream().map(Direccion::getProvincia).toList()));
      direccion.setMunicipio(this.buscadorFullTextSearch.crearNombreNormalizadoCon(hechoAmodificar.getDireccion().getMunicipio(),
              direccionesHechos.stream().map(Direccion::getMunicipio).toList()));
      direccion.marcarNormalizada();
      hechoAmodificar.setDireccion(direccion);
    } else if (direccion != null) {
      hechoAmodificar.setDireccion(direccion);
    }
    this.direccionRepository.save(hechoAmodificar.getDireccion());
  }
}
