package ar.edu.utn.frba.dds.servicioUsuario.servicios;

import ar.edu.utn.frba.dds.servicioUsuario.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Usuario;
import ar.edu.utn.frba.dds.servicioUsuario.models.repositories.IUsuarioRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class FuenteEstaticaService {
  private final WebClient webClient;
  private final IUsuarioRepositoryJPA usuarioRepository;

  public FuenteEstaticaService(@Value("${api.servicioFuenteEstatica.url}") String baseUrl, IUsuarioRepositoryJPA usuarioRepository) {
    this.webClient = WebClient.builder().baseUrl(baseUrl)
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(c -> c
                .defaultCodecs()
                .maxInMemorySize(50 * 1024 * 1024))
            .build())
        .build();
    this.usuarioRepository = usuarioRepository;
  }


  public String subirHechosCSV(MultipartFile archivo, Long idUsuario) {
    Usuario usuario = this.usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsuarioNoEncontrado("El usuario indicado no existe"));

    log.info("[FuenteEstaticaService] reenviando CSV a servicioFuenteEstatica para usuario {}", idUsuario);

    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("archivo", archivo.getResource())
        .filename(archivo.getOriginalFilename())
        .contentType(archivo.getContentType() != null
            ? MediaType.parseMediaType(archivo.getContentType())
            : MediaType.TEXT_PLAIN);
    builder.part("usuario", idUsuario.toString());

    return webClient.post()
        .uri("/api/hechos")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build()))
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }
}
