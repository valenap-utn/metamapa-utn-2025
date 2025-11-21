package ar.edu.utn.frba.dds.servicioUsuario.servicios;

import ar.edu.utn.frba.dds.servicioUsuario.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoEstadisticasDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Usuario;
import ar.edu.utn.frba.dds.servicioUsuario.models.repositories.IUsuarioRepositoryJPA;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EstadisticaService {
  private final IUsuarioRepositoryJPA usuarioRepository;
  private final WebClient webClient;

  public EstadisticaService(IUsuarioRepositoryJPA usuarioRepository, @Value("${api.servicioEstadistica.url}") String baseURL) {
    this.usuarioRepository = usuarioRepository;
    this.webClient = WebClient.builder().baseUrl(baseURL).build();
  }

  public ConjuntoEstadisticasDTO getEstadisticas() {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/estadisticas").build())
            .retrieve()
            .bodyToMono(ConjuntoEstadisticasDTO.class).block();
  }

  private Usuario obtenerUsuario() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    return this.usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioNoEncontrado("El usuario indicado no existe"));
  }

  public MultipartFile getEstadisticasEnCSV() {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/estadisticas/estadisticas.csv").build())
            .retrieve()
            .bodyToMono(MultipartFile.class).block();
  }
}
