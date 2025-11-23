package ar.edu.utn.frba.dds.metamapa_client.clients;

import ar.edu.utn.frba.dds.metamapa_client.clients.utils.JwtUtil;
import ar.edu.utn.frba.dds.metamapa_client.services.internal.WebApiCallerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;


@Component
@Slf4j
public class FuenteEstatica implements IFuenteEstatica {
  private final WebClient webClient;
  private final WebApiCallerService webApiCallerService;
  private final JwtUtil jwtUtil;
  public FuenteEstatica(@Value("${api.servicioUsuarios.url}") String baseUrl, WebApiCallerService webApiCallerService, JwtUtil jwtUtil) {
    this.webApiCallerService = webApiCallerService;
    this.jwtUtil = jwtUtil;
    log.info("[FuenteEstatica] baseUrl configurada = {}", baseUrl);
    this.webClient = WebClient.builder()
        .baseUrl(baseUrl)
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(codecs -> codecs
                .defaultCodecs()
                .maxInMemorySize(50 * 1024 * 1024))
            .build()).build();
  }

  @Override
  public String subirHechosCSV(MultipartFile archivo) {
    log.info("[FuenteEstaticaClient] Enviando CSV a servicioUsuario: file='{}', size={}",
        archivo.getOriginalFilename(), archivo.getSize());

    try {
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
      HttpServletRequest request = attributes.getRequest();

      String accessToken = (String) request.getSession().getAttribute("accessToken");
      if (accessToken == null) {
        throw new IllegalStateException("No hay accessToken en la sesión. El usuario no está logueado.");
      }

      Long userId = jwtUtil.getId(accessToken);
      log.info("[FuenteEstaticaClient] idUsuario extraído del token = {}", userId);

      // Armamos multipart para el servicioUsuario
      MultipartBodyBuilder builder = new MultipartBodyBuilder();
      builder.part("archivo", archivo.getResource())
          .filename(archivo.getOriginalFilename())
          .contentType(archivo.getContentType() != null
              ? MediaType.parseMediaType(archivo.getContentType())
              : MediaType.TEXT_PLAIN);

      builder.part("usuario", userId.toString());

      // Llamamos al servicioUsuario
      return webClient.post()
          .uri("/api/fuenteEstatica/hechos")
          .headers(h -> h.setBearerAuth(accessToken))
          .contentType(MediaType.MULTIPART_FORM_DATA)
          .body(BodyInserters.fromMultipartData(builder.build()))
          .retrieve()
          .bodyToMono(String.class)
          .block();

    } catch (Exception e) {
      log.error("[FuenteEstaticaClient] Error subiendo CSV", e);
      throw new RuntimeException("Error subiendo CSV al servicioUsuario", e);
    }
  }

  /*public String subirHechosCSV(MultipartFile archivo, Long idUsuario){
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
  }*/
}
