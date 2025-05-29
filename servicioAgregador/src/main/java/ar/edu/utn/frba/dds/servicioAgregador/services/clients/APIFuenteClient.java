package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class APIFuenteClient {
  private final WebClient webClient;

  APIFuenteClient(String baseUrl) {
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
  }

  public Mono<Fuente> setFuenteConHechosAPI(Fuente fuente) {
    return this.mapAFuenteConHechos(
            webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/hechos").build())
                    .retrieve(), fuente);
  }

  protected abstract  Mono<Fuente> mapAFuenteConHechos(WebClient.ResponseSpec retrieve, Fuente fuente);

  protected <T> Fuente cargarHechosMapeadosEnFuente(ConjuntoHechoDTO<T> response, Fuente fuente) {
    Set<Hecho> hechos = response.getHechos().stream().map(this::toHecho).collect(Collectors.toSet());
    fuente.actualizarHechos(hechos);
    return fuente;
  }

  protected <R> Hecho toHecho(HechoDTO<R> hechoDTO) {
    Hecho hecho = new Hecho();
    hecho.setId(hechoDTO.getId());
    hecho.setTitulo(hechoDTO.getTitulo());
    hecho.setDescripcion(hechoDTO.getDescripcion());
    hecho.setCategoria(hechoDTO.getCategoria());
    hecho.setUbicacion(hechoDTO.getUbicacion());
    hecho.setFechaAcontecimiento(hechoDTO.getFechaAcontecimiento());
    hecho.setFechaCarga(hechoDTO.getFechaCarga());
    return this.completarHecho(hecho, hechoDTO);
  }

  protected abstract <R> Hecho completarHecho(Hecho hecho, HechoDTO<R> hechoDTO);
}
