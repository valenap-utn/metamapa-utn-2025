package ar.edu.utn.frba.dds.metamapa_client.clients;

import ar.edu.utn.frba.dds.metamapa_client.dtos.*;
import ar.edu.utn.frba.dds.metamapa_client.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.ConjuntoSolicitudesEliminacionOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.SolicitudEliminacionDTO;
import ar.edu.utn.frba.dds.metamapa_client.services.internal.WebApiCallerService;
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
  public ServicioAgregador(@Value("${agregador.api.base-url}") String baseUrl, WebClient agregadorWebClient, WebApiCallerService webApiCallerService) {
    this.baseUrl = baseUrl;
    this.agregadorWebClient = agregadorWebClient;
    this.webApiCallerService = webApiCallerService;
  }


  public List<HechoDTOOutput> findAllHechos(FiltroDTO filtroDTO) {
    return this.agregadorWebClient.get().uri(uriBuilder -> this.uriConFiltros(filtroDTO, uriBuilder, "/api/agregador/hechos").build())
            .retrieve()
            .bodyToMono(ConjuntoHechoDTO.class).map(
                    ConjuntoHechoDTO::getHechos
            ).block();
  }

  public List<HechoDTOOutput> findHechosByColeccionId(UUID coleccionId, FiltroDTO filtroDTO) {
    return this.agregadorWebClient.get().uri(uriBuilder -> this.uriConFiltros(filtroDTO, uriBuilder, "/api/agregador/colecciones/{id}/hechos").build(coleccionId))
            .retrieve().bodyToMono(ConjuntoHechoDTO.class).map(
                    ConjuntoHechoDTO::getHechos
            ).block();
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
              .queryParam("longitud", filtroDTO.getLongitud());
    }
    return uriAMedioConstruir;
  }

  public List<SolicitudEliminacionDTO> findAllSolicitudes() {
    return this.agregadorWebClient.get().uri(uriBuilder -> uriBuilder.path("/api/agregador/solicitudes").build())
            .retrieve().bodyToMono(ConjuntoSolicitudesEliminacionOutput.class)
            .map( ConjuntoSolicitudesEliminacionOutput::getSolicitudes)
            .block();
  }

  public SolicitudEliminacionDTO crearSolicitud(SolicitudEliminacionDTO solicitudEliminacionDTO) {
    return this.agregadorWebClient.post().uri(uriBuilder -> uriBuilder.path("/api/solicitudes").build())
            .bodyValue(solicitudEliminacionDTO).retrieve()
            .bodyToMono(SolicitudEliminacionDTO.class)
            .block();
  }

  public SolicitudEliminacionDTO cancelarSolicitud(Long idSolicitud) {
    return this.agregadorWebClient.patch().uri(uriBuilder -> uriBuilder.path("/api/solicitudes/{id}").build(idSolicitud))
            .retrieve().bodyToMono(SolicitudEliminacionDTO.class)
            .block();
  }

  public SolicitudEliminacionDTO aceptarSolicitud(Long idSolicitud) {
    return this.agregadorWebClient.delete().uri(uriBuilder -> uriBuilder.path("/api/solicitudes/{id}").build(idSolicitud))
            .retrieve().bodyToMono(SolicitudEliminacionDTO.class)
            .block();
  }

  public ColeccionDTOOutput modificarColeccion(ColeccionDTOInput coleccionDTOInput, UUID coleccionId) {
    return this.agregadorWebClient.put().uri(uriBuilder -> uriBuilder.path("/api/colecciones/{id}").build(coleccionId))
            .bodyValue(coleccionDTOInput)
            .retrieve().bodyToMono(ColeccionDTOOutput.class).block();
  }

  public ColeccionDTOOutput eliminarColeccion(UUID idColeccion) {
    return this.agregadorWebClient.delete().uri(uriBuilder -> uriBuilder.path("/api/colecciones/{id}").build(idColeccion))
            .retrieve().bodyToMono(ColeccionDTOOutput.class).block();
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
