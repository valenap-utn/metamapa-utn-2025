package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Origen;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class ConexionFuenteService {
  private WebClient webClient;

  public abstract Set<Hecho> getHechosFuente();
  public abstract Mono<Fuente> getHechosActualizados();

  ConexionFuenteService(String baseUrl) {
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
  }

  protected Mono<Set<Hecho>> setHechosFuente() {
    return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/hechos").build())
            .retrieve()
            .bodyToMono(this.tipoDTOPorFuente())
            .map( response -> {
              return response.stream().map(this::toHecho).collect(Collectors.toSet());
            });
  }

  protected abstract Hecho toHecho(HechoDTO hechoDTO);

  protected abstract ConjuntoHechoDTO tipoDTOPorFuente();

  protected abstract Origen getOrigen(HechoDTO hechoDTO);
}
