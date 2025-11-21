package ar.edu.utn.frba.dds.metamapa_client.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FuenteEstatica implements IFuenteEstatica {
  private final WebClient webClient;

  public FuenteEstatica(@Value("${api.servicioFuenteEstatica.url}") String baseUrl) {
    this.webClient = WebClient.builder()
        .baseUrl(baseUrl)
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(codecs -> codecs
                .defaultCodecs()
                .maxInMemorySize(50 * 1024 * 1024))
            .build()).build();
  }

  public String subirHechosCSV(MultipartFile archivo, Long idUsuario){
    MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
    bodyBuilder
        .part("archivo", archivo.getResource())
        .filename(archivo.getOriginalFilename());

    return webClient
        .post()
        .uri(uriBuilder -> uriBuilder
            .path("/api/hechos")
            .queryParam("idUsuario", idUsuario)
            .build())
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

}
