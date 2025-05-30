package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoEstatica;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapperAPIClient;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class APIFuenteClient {
  private final WebClient webClient;
  @Getter
  private final MapperAPIClient mapper;

  APIFuenteClient(String baseUrl, MapperAPIClient mapper) {
    this.mapper = mapper;
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
  }

  public Mono<Fuente> setFuenteConHechosAPI(Fuente fuente) {
    return this.mapAFuenteConHechos(
            webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/hechos").build())
                    .retrieve(), fuente);
  }

  protected abstract  Mono<Fuente> mapAFuenteConHechos(WebClient.ResponseSpec retrieve, Fuente fuente);

  protected Fuente cargarHechosMapeadosEnFuente(Set<Hecho> hechos, Fuente fuente) {
    fuente.actualizarHechos(hechos);
    return fuente;
  }

}
