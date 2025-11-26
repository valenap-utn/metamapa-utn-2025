package ar.edu.utn.frba.dds.servicioUsuario.servicios;

import ar.edu.utn.frba.dds.servicioUsuario.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoCategorias;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoSolicitudesEdicionOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOInput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.SolicitudEdicionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Usuario;
import ar.edu.utn.frba.dds.servicioUsuario.models.repositories.IUsuarioRepositoryJPA;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
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

  public HechoDTOOutput crearHecho(HechoDTOInput hecho, MultipartFile contenidoMultimedia, String baseUrl) {
    Usuario usuario = null;
    if (hecho.getIdUsuario() != null) {
      usuario = this.usuarioRepository.findById(hecho.getIdUsuario()).orElse(null);
    }
    hecho.setUsuario(usuario != null ? usuario.getUsuarioDTO(): null);
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    if (contenidoMultimedia != null) {
      builder.part("contenidomultimedia", contenidoMultimedia.getResource());
    }

    builder.part("hecho", hecho);

    return initWebClient(baseUrl).post().uri(uriBuilder -> uriBuilder.path("/api/hechos").build())
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .retrieve()
            .bodyToMono(HechoDTOOutput.class).block();
  }


  public HechoDTOOutput revisarHecho(Long idHecho, String baseUrl, RevisionDTO revisionDTO) {
    Usuario usuario = this.obtenerUsuario();
    if (usuario == null) {
      throw new UsuarioNoEncontrado("El usuario no existe");
    }
    revisionDTO.setUsuario(usuario.getUsuarioDTO());
    return initWebClient(baseUrl).post().uri(uriBuilder -> uriBuilder.path("/api/hechos/{id}/revisados").build(idHecho))
            .bodyValue(revisionDTO).retrieve().bodyToMono(HechoDTOOutput.class).block();
  }

  public SolicitudEdicionDTO solicitarModificacion(SolicitudEdicionDTO solicitudEdicion, MultipartFile contenidoMultimedia, String baseUrl) {
    Usuario usuario = this.obtenerUsuario();
    solicitudEdicion.setUsuario(usuario.getUsuarioDTO());
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    if (contenidoMultimedia != null) {
      builder.part("contenidomultimedia", contenidoMultimedia.getResource());
    }
    builder.part("solicitud", solicitudEdicion);

    return initWebClient(baseUrl).post().uri(uriBuilder -> uriBuilder.path("/api/solicitudes").build())
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(builder.build()))
            .retrieve()
            .bodyToMono(SolicitudEdicionDTO.class).block();
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

  public ConjuntoSolicitudesEdicionOutput findAllSolicitudes(String baseUrl) {
    return this.initWebClient(baseUrl).get().uri(uriBuilder -> uriBuilder.path("/api/solicitudes").build())
            .retrieve()
            .bodyToMono(ConjuntoSolicitudesEdicionOutput.class)
            .block();
  }

  public ConjuntoHechoDTO findHechosPorUsuario(Long id, String baseUrl) {
    return this.initWebClient(baseUrl).get().uri(uriBuilder -> uriBuilder.path("/api/hechos").queryParam("idUsuario", id).build())
            .retrieve()
            .bodyToMono(ConjuntoHechoDTO.class)
            .block();
  }

  public HechoDTOOutput findHechoById(Long id, String baseUrl) {
    return this.initWebClient(baseUrl).get().uri(uriBuilder -> uriBuilder.path("/api/hechos/{id}").build(id))
            .retrieve()
            .bodyToMono(HechoDTOOutput.class)
            .block();
  }

  public ConjuntoCategorias findAllCategorias(String baseUrl) {
    return this.initWebClient(baseUrl).get().uri(uriBuilder -> uriBuilder.path("/api/categorias").build())
            .retrieve().bodyToMono(ConjuntoCategorias.class).block();
  }

  //Para hechos nuevos
  public ConjuntoHechoDTO findNuevosHechos(String baseUrl, String estado) {
    return this.initWebClient(baseUrl)
        .get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/nuevos-hechos")
            .queryParam("estado",estado)
            .build())
        .retrieve()
        .bodyToMono(ConjuntoHechoDTO.class)
        .block();
  }
}
