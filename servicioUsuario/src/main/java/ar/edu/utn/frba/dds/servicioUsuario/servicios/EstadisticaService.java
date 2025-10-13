package ar.edu.utn.frba.dds.servicioUsuario.servicios;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoHechoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EstadisticaService {
  @Value("${api.servicioEstadistica.url}")
  private String baseURL;
  private final WebClient webClient;

  public EstadisticaService() {
    this.webClient = WebClient.builder().baseUrl(baseURL).build();
  }

  public Object getEstadisticas() {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/estadisticas").build())
            .retrieve()
            .bodyToMono(ConjuntoHechoDTO.class).map(
                    ConjuntoHechoDTO::getHechos
            ).block();
  }

  public Object getEstadisticasEnCSV() {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/estadisticas/estadisticas.csv").build())
            .retrieve()
            .bodyToMono(ConjuntoHechoDTO.class).map(
                    ConjuntoHechoDTO::getHechos
            ).block();
  }
}
