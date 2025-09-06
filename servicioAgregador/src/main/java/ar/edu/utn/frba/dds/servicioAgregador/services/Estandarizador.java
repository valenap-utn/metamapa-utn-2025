package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Direccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.IBuscadorFullTextSearch;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.ICategoriaRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.IDireccionRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class Estandarizador implements IEstandarizador {
  private final IHechoRepository hechoRepository;
  private final ICategoriaRepository categoriaRepository;
  private final IBuscadorFullTextSearch buscadorFullTextSearch;
  private final IDireccionRepository direccionRepository;

  public Estandarizador(IHechoRepository hechoRepository, ICategoriaRepository categoriaRepository, IBuscadorFullTextSearch buscadorFullTextSearch, IDireccionRepository direccionRepository) {
    this.hechoRepository = hechoRepository;
    this.categoriaRepository = categoriaRepository;
    this.buscadorFullTextSearch = buscadorFullTextSearch;
    this.direccionRepository = direccionRepository;
  }
  @Override
  public Mono<Void> estandarizarHechos() {
    List<Hecho> hechos = hechoRepository.findByNormalizado(false);
    List<Hecho> hechosCopia = new ArrayList<>(hechos);
    return Flux.fromIterable(hechos)
            .flatMap(hecho -> this.estandarizarHecho(hecho, hechosCopia)).then();
  }

  private Mono<Void> estandarizarHecho(Hecho hecho, List<Hecho> hechosDesnormalizados) {
    return Mono.just(hecho).map(hechoAmodificar ->
            {
              this.estandarizarTitulo(hechoAmodificar, hechosDesnormalizados);
              this.estandarizarCategoria(hechoAmodificar, hechosDesnormalizados);
              this.estandarizarFechas(hechoAmodificar, hechosDesnormalizados);
              this.estandarizarUbicacion(hechoAmodificar, hechosDesnormalizados);
              hechoAmodificar.marcarComoNormalizado();
              return Mono.empty();
            }
            ).then();
  }





  private void estandarizarFechas(Hecho hechoAmodificar, List<Hecho> hechosDesnormalizados) {
    hechoAmodificar.setFechaCarga(this.estandarizarFecha(hechoAmodificar.getFechaCarga()));
    hechoAmodificar.setFechaAcontecimiento(this.estandarizarFecha(hechoAmodificar.getFechaCarga()));
  }

  private LocalDateTime estandarizarFecha(LocalDateTime fechaCarga) {
    //TODO
    return null;
  }

  private void estandarizarCategoria(Hecho hechoAmodificar, List<Hecho> hechosDesnormalizados) {
    List<Categoria> categorias = this.categoriaRepository.findByFullTextSearch(hechoAmodificar.getNombreCategoria());
    Categoria categoria = categorias.stream().findFirst().orElse(null);
    if (categoria == null) {
      categoria = new Categoria();
      List<String> categoriasHechos =  hechosDesnormalizados.stream().map(Hecho::getNombreCategoria).toList();
      categoria.setNombre(this.buscadorFullTextSearch.crearNombreNormalizadoCon(hechoAmodificar.getNombreCategoria(), categoriasHechos));
      this.categoriaRepository.save(categoria);
    }
    hechoAmodificar.setCategoria(categoria);
  }

  private void estandarizarTitulo(Hecho hechoAmodificar, List<Hecho> hechosDesnormalizados) {
    List<Hecho> hechos = this.hechoRepository.findByFullTextSearch(hechoAmodificar.getTitulo());
    String titulo = hechos.stream().map(Hecho::getTitulo).findFirst().orElse(null);
    if (titulo == null) {
      List<String> titulos =  hechosDesnormalizados.stream().map(Hecho::getTitulo).toList();
      titulo = this.buscadorFullTextSearch.crearNombreNormalizadoCon(hechoAmodificar.getTitulo(), titulos);
    }
    hechoAmodificar.setTitulo(titulo);
  }

  private void estandarizarUbicacion(Hecho hechoAmodificar, List<Hecho> hechosDesnormalizados) {
    //Muy TODO
    List<Direccion> direcciones = this.direccionRepository.findByFullTextSearch(hechoAmodificar.getNombreCategoria());
    Direccion direccion = direcciones.stream().findFirst().orElse(null);
    if (direccion == null) {
      List<String> titulos =  hechosDesnormalizados.stream().map(Hecho::getDireccion).toList();
      direccion.setCiudad(this.buscadorFullTextSearch.crearNombreNormalizadoCon(hechoAmodificar.getDireccion().getCiudad(), titulos));
      direccion.setLocalidad(this.buscadorFullTextSearch.crearNombreNormalizadoCon(hechoAmodificar.getDireccion().getCiudad(), titulos));
      direccion.setProvincia(this.buscadorFullTextSearch.crearNombreNormalizadoCon(hechoAmodificar.getDireccion().getCiudad(), titulos));
      direccion.setMunicipio(this.buscadorFullTextSearch.crearNombreNormalizadoCon(hechoAmodificar.getDireccion().getCiudad(), titulos));
    }
    hechoAmodificar.setDireccion(direccion);

  }
}
