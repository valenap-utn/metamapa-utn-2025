package ar.edu.utn.frba.dds.metamapa_client.clients;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Component
@Slf4j
public class FuenteEstatica implements IFuenteEstatica {
  private final WebClient webClient;

  public FuenteEstatica(@Value("${api.servicioFuenteEstatica.url}") String baseUrl) {
    log.info("[FuenteEstatica] baseUrl configurada = {}", baseUrl);
    this.webClient = WebClient.builder()
        .baseUrl(baseUrl)
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(codecs -> codecs
                .defaultCodecs()
                .maxInMemorySize(50 * 1024 * 1024))
            .build()).build();
  }

  public String subirHechosCSV(MultipartFile archivo, Long idUsuario){
    log.info("[FuenteEstatica] Preparando multipart idUsuario={}", idUsuario);

    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String accessToken = (String) request.getSession().getAttribute("accessToken");

    MultipartBodyBuilder builder = new MultipartBodyBuilder();

    try {
      ByteArrayResource fileResource = new ByteArrayResource(archivo.getBytes()) {
        @Override
        public String getFilename() {
          return archivo.getOriginalFilename(); // *** CRÍTICO ***
        }
      };

      builder.part("archivo", fileResource)
          .filename(archivo.getOriginalFilename())
          .contentType(MediaType.TEXT_PLAIN);

      builder.part("usuario", idUsuario.toString())
          .contentType(MediaType.TEXT_PLAIN);

      return webClient.post()
          .uri("/api/hechos")
          .headers(h -> {
            if (accessToken != null) {
              h.setBearerAuth(accessToken);
            }
          })
          .contentType(MediaType.MULTIPART_FORM_DATA) // OK
          .body(BodyInserters.fromMultipartData(builder.build()))
          .retrieve()
          .bodyToMono(String.class)
          .block();

    } catch (IOException e) {
      throw new RuntimeException("Error leyendo archivo CSV", e);
    }
  }


}
