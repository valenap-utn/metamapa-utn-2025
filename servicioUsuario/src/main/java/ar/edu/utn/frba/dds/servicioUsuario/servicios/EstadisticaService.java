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
    Usuario usuario = this.obtenerUsuario();
    return this.webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/estadisticas")
            .queryParam("idUsuario", usuario.getId())
            .build())
            .retrieve()
            .bodyToMono(ConjuntoEstadisticasDTO.class).block();
  }

  private Usuario obtenerUsuario() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    return this.usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioNoEncontrado("El usuario indicado no existe"));
  }

  /**
   * MultipartFile es para subir archivos desde el cliente al servidor.
   * Para descargar archivos, lo normal es trabajar con byte[] (o Resource) y headers HTTP.
   * MultipartFile es una abstracción de Spring que representa una parte de una request multipart/form-data
   */
  /*public MultipartFile getEstadisticasEnCSV() {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/estadisticas/estadisticas.csv").build())
            .retrieve()
            .bodyToMono(MultipartFile.class).block();
  }*/
  public byte[] getEstadisticasEnCSV() {
    Usuario usuario = this.obtenerUsuario();

    return this.webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/estadisticas/estadisticas.csv")
            .queryParam("idUsuario", usuario.getId())
            .build())
        .retrieve()
        .bodyToMono(byte[].class).block();
  }
}
