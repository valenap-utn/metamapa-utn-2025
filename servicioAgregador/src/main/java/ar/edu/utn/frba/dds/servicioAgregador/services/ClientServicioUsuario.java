package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.UsuarioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientServicioUsuario {
  private final WebClient webClient;


  public ClientServicioUsuario(@Value("{client.servicio.usuario}") String baseURL) {
    this.webClient = WebClient.builder().baseUrl(baseURL).build();
  }

  public UsuarioDTO findUsuarioById(Long id) {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/api/{id}").build(id))
            .retrieve().bodyToMono(UsuarioDTO.class).block();
  }
}
