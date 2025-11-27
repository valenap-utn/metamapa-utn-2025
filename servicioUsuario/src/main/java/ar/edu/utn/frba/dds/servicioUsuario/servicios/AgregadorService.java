package ar.edu.utn.frba.dds.servicioUsuario.servicios;

import ar.edu.utn.frba.dds.servicioUsuario.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoCategorias;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoColeccion;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoSolicitudesEliminacionOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.SolicitudEliminacionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Usuario;
import ar.edu.utn.frba.dds.servicioUsuario.models.repositories.IUsuarioRepositoryJPA;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

@Service
public class AgregadorService {
  private final WebClient webClient;
  private final IUsuarioRepositoryJPA usuarioRepository;

  public AgregadorService(@Value("${api.servicioAgregador.url}") String baseURL, IUsuarioRepositoryJPA usuarioRepository) {
    this.webClient = WebClient.builder().baseUrl(baseURL).build();
    this.usuarioRepository = usuarioRepository;
  }



  public ConjuntoHechoDTO findAllHechos(FiltroDTO filtroDTO) {
    return this.webClient.get().uri(uriBuilder -> this.uriConFiltros(filtroDTO, uriBuilder, "/api/hechos").build())
            .retrieve()
            .bodyToMono(ConjuntoHechoDTO.class).block();
  }

  public ConjuntoHechoDTO findHechosByColeccionId(UUID coleccionId, FiltroDTO filtroDTO) {
    return this.webClient.get().uri(uriBuilder -> this.uriConFiltros(filtroDTO, uriBuilder, "/api/colecciones/{id}/hechos").build(coleccionId))
            .retrieve().bodyToMono(ConjuntoHechoDTO.class).block();
  }

  private UriBuilder uriConFiltros(FiltroDTO filtroDTO, UriBuilder uriBuilder, String path) {
    UriBuilder uriAMedioConstruir = uriBuilder.path(path);
    if(filtroDTO != null) {
      uriAMedioConstruir.queryParam("categoria", filtroDTO.getCategoria())
              .queryParam("fecha_reporte_desde", filtroDTO.getFecha_reporte_desde())
              .queryParam("fecha_reporte_hasta", filtroDTO.getFecha_reporte_hasta())
              .queryParam("fecha_acontecimiento_desde", filtroDTO.getFecha_acontecimiento_desde())
              .queryParam("fecha_acontecimiento_hasta", filtroDTO.getFecha_acontecimiento_hasta())
              .queryParam("latitud", filtroDTO.getLatitud())
              .queryParam("longitud", filtroDTO.getLongitud())
              .queryParam("provincia", filtroDTO.getProvincia())
              .queryParam("municipio", filtroDTO.getMunicipio())
              .queryParam("departamento", filtroDTO.getDepartamento())
              .queryParam("nroPagina", filtroDTO.getNroPagina());
    }
    return uriAMedioConstruir;
  }

  public ConjuntoSolicitudesEliminacionOutput findAllSolicitudes() {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/api/solicitudes").build())
            .retrieve().bodyToMono(ConjuntoSolicitudesEliminacionOutput.class)
            .block();
  }

  public SolicitudEliminacionDTO crearSolicitud(SolicitudEliminacionDTO solicitudEliminacionDTO) {
    UsuarioDTO usuario = null;
    if (solicitudEliminacionDTO.getIdusuario() != null) {
      Usuario usuarioBuscado = this.usuarioRepository.findById(solicitudEliminacionDTO.getIdusuario()).orElse(null);
      usuario = usuarioBuscado == null ? null : usuarioBuscado.getUsuarioDTO();
    }
    solicitudEliminacionDTO.setUsuario(usuario);
    return this.webClient.post().uri(uriBuilder -> uriBuilder.path("/api/solicitudes").build())
            .bodyValue(solicitudEliminacionDTO).retrieve()
            .bodyToMono(SolicitudEliminacionDTO.class)
            .block();
  }

  public SolicitudEliminacionDTO cancelarSolicitud(Long idSolicitud, RevisionDTO revisionDTO) {
    Usuario usuario = this.obtenerUsuario();
    revisionDTO.setUsuario(usuario.getUsuarioDTO());
    return this.webClient.post().uri(uriBuilder -> uriBuilder.path("/api/solicitudes/{id}/eliminados").build(idSolicitud))
            .retrieve().bodyToMono(SolicitudEliminacionDTO.class)
            .block();
  }

  public SolicitudEliminacionDTO aceptarSolicitud(Long idSolicitud, RevisionDTO revisionDTO) {
    Usuario usuario = this.obtenerUsuario();
    revisionDTO.setUsuario(usuario.getUsuarioDTO());
    return this.webClient.post().uri(uriBuilder -> uriBuilder.path("/api/solicitudes/{id}/eliminados").build(idSolicitud))
            .retrieve().bodyToMono(SolicitudEliminacionDTO.class)
            .block();
  }

  public ColeccionDTOOutput modificarColeccion(ColeccionDTOInput coleccionDTOInput, UUID coleccionId) {
    Usuario usuario = this.obtenerUsuario();
    coleccionDTOInput.setUsuario(usuario.getUsuarioDTO());
    return this.webClient.put().uri(uriBuilder -> uriBuilder.path("/api/colecciones/{id}").build(coleccionId))
            .bodyValue(coleccionDTOInput)
            .retrieve().bodyToMono(ColeccionDTOOutput.class).block();
  }

  public ColeccionDTOOutput eliminarColeccion(UUID idColeccion) {
    Usuario usuario = this.obtenerUsuario();
    return this.webClient.delete().uri(uriBuilder -> uriBuilder.path("/api/colecciones/{id}").queryParam("idUsuario", usuario.getId()).build(idColeccion))
            .retrieve().bodyToMono(ColeccionDTOOutput.class).block();
  }

  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccion) {
    Usuario usuario = this.obtenerUsuario();
    coleccion.setUsuario(usuario.getUsuarioDTO());
    return this.webClient.post().uri(uriBuilder -> uriBuilder.path("/api/colecciones").build())
            .bodyValue(coleccion)
            .retrieve().bodyToMono(ColeccionDTOOutput.class).block();
  }

  private Usuario obtenerUsuario() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    return this.usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioNoEncontrado("El usuario indicado no existe"));
  }

  public HechoDTOOutput getHecho(Long idHecho) {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/api/hechos/{id}").build(idHecho))
            .retrieve().bodyToMono(HechoDTOOutput.class).block();
  }

  public ConjuntoColeccion findColecciones() {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/api/colecciones").build())
            .retrieve().bodyToMono(ConjuntoColeccion.class).block();
  }

  public ConjuntoHechoDTO findHechosByIdUsuario(Long idUsuario) {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/api/hechos").queryParam("idUsuario", idUsuario).build())
            .retrieve().bodyToMono(ConjuntoHechoDTO.class).block();
  }

  public ConjuntoCategorias findAllCategorias() {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/api/categorias").build())
            .retrieve().bodyToMono(ConjuntoCategorias.class).block();
  }

  public ColeccionDTOOutput findColeccionById(UUID idColeccion) {
    return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/api/colecciones/{idColeccion}").build(idColeccion))
            .retrieve().bodyToMono(ColeccionDTOOutput.class).block();
  }
}
