package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Origen;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class ConexionFuenteService {
  private WebClient webClient;

  public abstract void getHechosFuente(Fuente fuente);
  public abstract Mono<Fuente> actualizarHechosFuente(Fuente fuente);

  ConexionFuenteService(String baseUrl) {
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
  }

  protected Mono<Fuente> setHechos(Fuente fuente) {
    return this.mapearAMonoConjuntoHechoDTO(
            webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/hechos").build())
            .retrieve()).map( response -> {
              Set<Hecho> hechos = response.getHechos().stream().map(this::toHecho).collect(Collectors.toSet());
              fuente.actualizarHechos(hechos);
              return fuente;
            });
  }

  protected abstract Mono<ConjuntoHechoDTO> mapearAMonoConjuntoHechoDTO(WebClient.ResponseSpec retrieve);

  protected abstract Hecho toHecho(HechoDTO hechoDTO);

}
