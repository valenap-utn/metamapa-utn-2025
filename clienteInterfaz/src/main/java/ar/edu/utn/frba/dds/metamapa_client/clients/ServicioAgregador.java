package ar.edu.utn.frba.dds.metamapa_client.clients;

import ar.edu.utn.frba.dds.metamapa_client.clients.utils.JwtUtil;
import ar.edu.utn.frba.dds.metamapa_client.dtos.*;
import ar.edu.utn.frba.dds.metamapa_client.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.ConjuntoSolicitudesEliminacionOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.SolicitudEliminacionDTO;
import ar.edu.utn.frba.dds.metamapa_client.services.internal.WebApiCallerService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;

@Component
public class ServicioAgregador implements IServicioAgregador {
  private final String baseUrl;
  private final WebClient agregadorWebClient;
  private final WebApiCallerService webApiCallerService;
  private final JwtUtil jwtUtil;
  public ServicioAgregador(@Value("${agregador.api.base-url}") String baseUrl, WebClient agregadorWebClient, WebApiCallerService webApiCallerService, JwtUtil jwtUtil) {
    this.baseUrl = baseUrl;
    this.agregadorWebClient = agregadorWebClient;
    this.webApiCallerService = webApiCallerService;
    this.jwtUtil = jwtUtil;
  }


  public ConjuntoHechoDTO findAllHechos(FiltroDTO filtroDTO) {
    return this.agregadorWebClient.get().uri(uriBuilder -> this.uriConFiltros(filtroDTO, uriBuilder, "/api/agregador/hechos").build())
            .retrieve()
            .bodyToMono(ConjuntoHechoDTO.class).block();
  }

  public ConjuntoHechoDTO findHechosByColeccionId(UUID coleccionId, FiltroDTO filtroDTO) {
    return this.agregadorWebClient.get().uri(uriBuilder -> this.uriConFiltros(filtroDTO, uriBuilder, "/api/agregador/colecciones/{id}/hechos").build(coleccionId))
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

  public List<SolicitudEliminacionDTO> findAllSolicitudes() {
    return this.webApiCallerService.get(
            baseUrl + "/api/agregador/solicitudes",
            ConjuntoSolicitudesEliminacionOutput.class
    ).getSolicitudes();
  }

  public SolicitudEliminacionDTO crearSolicitud(Long idHecho, String justificacion, HttpSession session) {
    String accessToken = (String) session.getAttribute("accessToken");
    Long userId = null;
    if (accessToken != null) {
      userId = jwtUtil.getId(accessToken);
    }

    if (justificacion == null || justificacion.trim().length() < 500) {
      //return ResponseEntity.badRequest().body("La justificación debe tener al menos 500 caracteres");
    }

    SolicitudEliminacionDTO solicitud = new SolicitudEliminacionDTO();
    solicitud.setIdHecho(idHecho);
    solicitud.setIdusuario(userId);
    solicitud.setJustificacion(justificacion);
    solicitud.setEstado("PENDIENTE");
    solicitud.setFechaSolicitud(LocalDateTime.now());
    return this.agregadorWebClient.post().uri(uriBuilder -> uriBuilder.path("/api/agregador/solicitudes").build())
            .bodyValue(solicitud).retrieve()
            .bodyToMono(SolicitudEliminacionDTO.class)
            .block();
  }

  @Override
  public Long getCantidadHechos() {
    return this.webApiCallerService.get(
            baseUrl + "/api/agregador/hechos/cantidad",
            Long.class
    );
  }

  @Override
  public long getCantidadFuentes() {
    return this.webApiCallerService.get(
            baseUrl + "/api/agregador/fuentes/cantidad",
            Long.class
    );
  }

  public SolicitudEliminacionDTO cancelarSolicitud(Long idSolicitud, RevisionDTO revisionDTO) {
    return this.webApiCallerService.post(
            baseUrl + "/api/agregador/solicitudes/" + idSolicitud +"/eliminados", revisionDTO, SolicitudEliminacionDTO.class);
  }

  public SolicitudEliminacionDTO aceptarSolicitud(Long idSolicitud, RevisionDTO revisionDTO) {
    return this.webApiCallerService.post(
            baseUrl + "/api/agregador/solicitudes/" + idSolicitud +"/aceptados", revisionDTO, SolicitudEliminacionDTO.class);
  }

  public ColeccionDTOOutput modificarColeccion(ColeccionDTOInput coleccionDTOInput, UUID coleccionId) {
    return this.webApiCallerService.put(
            baseUrl + "/api/agregador/colecciones/" + coleccionId, coleccionDTOInput, ColeccionDTOOutput.class);
  }

  public ColeccionDTOOutput eliminarColeccion(UUID idColeccion) {
    this.webApiCallerService.delete(
            baseUrl + "/api/agregador/colecciones/" + idColeccion);
    return new ColeccionDTOOutput();
  }

  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccion) {
    return this.webApiCallerService.post(
            baseUrl + "/api/agregador/colecciones" , coleccion, ColeccionDTOOutput.class);
  }

  public ColeccionDTOOutput revisarColeccion(UUID idColeccion){
    try{
      return agregadorWebClient
          .get()
          .uri("/api/agregador/colecciones/{id}",idColeccion)
          .retrieve()
          .bodyToMono(ColeccionDTOOutput.class)
          .block();
    }catch(WebClientResponseException e){
      if(e.getStatusCode() == HttpStatus.NOT_FOUND){
        return null;
      }
      throw e;
    }
  }

  public ColeccionDTOOutput actualizarColeccion(ColeccionDTOInput coleccion, UUID idColeccion){
    try {
      return agregadorWebClient
          .put()
          .uri("colecciones/{id}",idColeccion)
          .bodyValue(coleccion)
          .retrieve()
          .bodyToMono(ColeccionDTOOutput.class)
          .block();
    }catch(WebClientResponseException e){
      if(e.getStatusCode() == HttpStatus.NOT_FOUND){
        return null;
      }
      throw e;
    }
  }

  @Override
  public HechoDTOOutput getHecho(Long idHecho) {
    return agregadorWebClient
            .get()
            .uri("/api/agregador/hechos/{id}",idHecho)
            .retrieve()
            .bodyToMono(HechoDTOOutput.class)
            .block();
  }

  @Override
  public List<HechoDTOOutput> listHechosDelUsuario(Long userId) {
    return agregadorWebClient
            .get()
            .uri("/usuarios/{id}/hechos",userId)
            .retrieve()
            .bodyToMono(ConjuntoHechoDTO.class)
            .map(ConjuntoHechoDTO::getHechos)
            .block();
  }

  @Override
  public List<ColeccionDTOOutput> findColecciones() {
    return agregadorWebClient
            .get()
            .uri(uriBuilder -> uriBuilder.path("/api/agregador/colecciones").build())
            .retrieve()
            .bodyToMono(ConjuntoColeccion.class)
            .map(ConjuntoColeccion::getColecciones)
            .block();
  }

  @Override
  public HechoDTOOutput aprobarHecho(Long idHecho) {
    return null;
  }

  @Override
  public HechoDTOOutput rechazarHecho(Long idHecho) {
    return null;
  }

  @Override
  public List<SolicitudEdicionDTO> findAllSolicitudesEdicion() {
    return List.of();
  }



  @Override
  public SolicitudEdicionDTO procesarSolicitudEdicion(Long idSolicitud, String baseUrl, RevisionDTO revisionDTO) {
    return null;
  }

  @Override
  public String getNombreUsuario(Long id) {
    return "";
  }

  @Override
  public List<String> findAllCategorias() {
    return this.agregadorWebClient.get().uri(uriBuilder -> uriBuilder.path("/api/agregador/categorias").build())
        .retrieve().bodyToMono(ConjuntoCategorias.class)
        .map(ConjuntoCategorias::getCategorias)
        .block();
  }
}
