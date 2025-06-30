package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapHechoOutput;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapperAPIClient;
import java.util.Set;
import lombok.Getter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class APIFuenteClient {
  private final WebClient webClient;
  @Getter
  private final MapperAPIClient mapper;
  private final MapHechoOutput mapHechoOutput;

  APIFuenteClient(String baseUrl, MapperAPIClient mapper, MapHechoOutput mapHechoOutput) {
    this.mapper = mapper;
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    this.mapHechoOutput = mapHechoOutput;
  }

  public Mono<Fuente> setFuenteConHechosAPI(Fuente fuente) {
    return this.mapAFuenteConHechos(
            webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/hechos").build())
                    .retrieve(), fuente);
  }

  protected abstract  Mono<Fuente> mapAFuenteConHechos(WebClient.ResponseSpec retrieve, Fuente fuente);

  protected Fuente cargarHechosMapeadosEnFuente(Set<Hecho> hechos, Fuente fuente) {
    fuente.actualizarHechos(hechos);
    return fuente;
  }

  public void postEliminado(Hecho hecho, Long idHecho) {
    Hecho hechocopia = Hecho.builder().id(idHecho).categoria(hecho.getCategoria())
            .descripcion(hecho.getDescripcion()).fechaAcontecimiento(hecho.getFechaAcontecimiento())
            .eliminado(hecho.isEliminado()).fechaCarga(hecho.getFechaCarga()).build();
    this.webClient.put().uri("/api/hechos/{id}", idHecho)
            .bodyValue(this.mapHechoOutput.toHechoDTO(hechocopia));

  }

}
