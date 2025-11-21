package ar.edu.utn.frba.dds.servicioUsuario.servicios;

import ar.edu.utn.frba.dds.servicioUsuario.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Usuario;
import ar.edu.utn.frba.dds.servicioUsuario.models.repositories.IUsuarioRepositoryJPA;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Service
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


  public String subirHechosCSV(MultipartFile archivo, Long idUsuario, String baseURL) {
    Usuario usuario = this.usuarioRepository.findById(idUsuario).orElseThrow(() -> new UsuarioNoEncontrado("El usuario indicado no existe"));

    return initWebClient(baseURL).post().uri(uriBuilder -> uriBuilder.queryParam("archivo", archivo).build())
            .bodyValue(usuario.getUsuarioDTO())
            .retrieve()
            .bodyToMono(String.class)
            .block();
  }
}
