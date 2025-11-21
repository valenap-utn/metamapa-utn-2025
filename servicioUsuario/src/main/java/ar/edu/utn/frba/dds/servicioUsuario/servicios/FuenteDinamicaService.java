package ar.edu.utn.frba.dds.servicioUsuario.servicios;

import ar.edu.utn.frba.dds.servicioUsuario.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOInput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.SolicitudEdicionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Usuario;
import ar.edu.utn.frba.dds.servicioUsuario.models.repositories.IUsuarioRepositoryJPA;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FuenteDinamicaService {
  private final IUsuarioRepositoryJPA usuarioRepository;

  public FuenteDinamicaService(IUsuarioRepositoryJPA usuarioRepository) {
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

  private Usuario obtenerUsuario() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    return this.usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioNoEncontrado("El usuario indicado no existe"));
  }

  public HechoDTOOutput crearHecho(HechoDTOInput hecho, String baseUrl) {

    return initWebClient(baseUrl).post().uri(uriBuilder -> uriBuilder.path("/api/hechos").build())
            .bodyValue(hecho).retrieve().bodyToMono(HechoDTOOutput.class).block();
  }


  public HechoDTOOutput revisarHecho(Long idHecho, String baseUrl) {
    return initWebClient(baseUrl).put().uri(uriBuilder -> uriBuilder.path("/api/hechos/{id}/revisados").build(idHecho))
            .retrieve().bodyToMono(HechoDTOOutput.class).block();
  }

  public SolicitudEdicionDTO solicitarModificacion(SolicitudEdicionDTO solicitudEdicion, String baseUrl) {
    return initWebClient(baseUrl).post().uri(uriBuilder -> uriBuilder.path("/api/solicitudes").build())
            .bodyValue(solicitudEdicion)
            .retrieve()
            .bodyToMono(SolicitudEdicionDTO.class)
            .block();
  }

  public SolicitudEdicionDTO  procesarSolicitudEdicion(Long idSolicitud, String baseUrl, RevisionDTO revisionDTO) {
    return this.initWebClient(baseUrl).put().uri(uriBuilder -> uriBuilder.path("/api/solicitudes/{id}").build(idSolicitud)).bodyValue(revisionDTO)
            .retrieve().bodyToMono(SolicitudEdicionDTO.class)
            .block();
  }

  public ConjuntoHechoDTO findHechosPendientes(String baseUrl) {
    return this.initWebClient(baseUrl).get().uri(uriBuilder -> uriBuilder.path("/api/hechos").queryParam("pendientes", true).build())
            .retrieve()
            .bodyToMono(ConjuntoHechoDTO.class)
            .block();
  }
}
