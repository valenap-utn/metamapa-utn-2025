package ar.edu.utn.frba.dds.servicioUsuario.servicios;

import ar.edu.utn.frba.dds.servicioUsuario.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Usuario;
import ar.edu.utn.frba.dds.servicioUsuario.models.repositories.IUsuarioRepositoryJPA;
import lombok.extern.slf4j.Slf4j;
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
  private final IUsuarioRepositoryJPA usuarioRepository;

  public FuenteEstaticaService(IUsuarioRepositoryJPA usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  private WebClient initWebClient(String baseUrl) {
    return WebClient.builder().baseUrl(baseUrl)
            .exchangeStrategies(ExchangeStrategies
                    .builder()
                    .codecs(codecs -> codecs
                            .defaultCodecs()
                            .maxInMemorySize(50 * 1024 * 1024))
                    .build()).build();
  }


  public String subirHechosCSV(MultipartFile archivo, Long idUsuario, String baseUrl) {
    Usuario usuario = this.usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsuarioNoEncontrado("El usuario indicado no existe"));

    log.info("[FuenteEstaticaService] reenviando CSV a servicioFuenteEstatica para usuario {}", idUsuario);

    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("archivo", archivo.getResource());
    builder.part("usuario", usuario.getUsuarioDTO());

    return initWebClient(baseUrl).post()
        .uri("/api/hechos")
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .body(BodyInserters.fromMultipartData(builder.build()))
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }
}
