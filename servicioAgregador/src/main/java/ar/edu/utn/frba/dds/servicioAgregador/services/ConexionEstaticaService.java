package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoEstatica;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Origen;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ConexionEstaticaService extends ConexionFuenteService{
  public ConexionEstaticaService(String baseUrl) {
    super(baseUrl);
  }

  @Override
  public Mono<Fuente> getHechosFuente(Fuente fuente) {
    Fuente fuenteConHechos = this.hechoRepository.findByIDFuente(fuente.getId());
    return Mono.just(fuenteConHechos);
  }

  @Override
  public Mono<Fuente> actualizarHechosFuente(Fuente fuente) {
    return this.setHechos(fuente);
  }

  @Override
  protected Mono<ConjuntoHechoDTO> mapearAMonoConjuntoHechoDTO(WebClient.ResponseSpec retrieve) {
    return retrieve.bodyToMono(ConjuntoHechoEstatica.class);
  }

  @Override
  protected Hecho toHecho(HechoDTO hechoDTO) {
    Hecho hecho = new Hecho();
    hecho.setTitulo(hechoDTO.getTitulo());
    hecho.setDescripcion(hechoDTO.getDescripcion());
    hecho.setCategoria(hechoDTO.getCategoria());
    hecho.setUbicacion(hechoDTO.getUbicacion());
    hecho.setFechaAcontecimiento(hechoDTO.getFechaAcontecimiento());
    hecho.setFechaCarga(hechoDTO.getFechaCarga());
    hecho.setOrigen(Origen.DATASET);
    return hecho;
  }

}
