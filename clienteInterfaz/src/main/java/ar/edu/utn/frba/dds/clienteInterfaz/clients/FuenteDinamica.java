package ar.edu.utn.frba.dds.clienteInterfaz.clients;

import ar.edu.utn.frba.dds.clienteInterfaz.dtos.ConjuntoCategorias;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.ConjuntoSolicitudesEdicionOutput;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.HechoDTOInput;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.SolicitudEdicionDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.services.internal.WebApiCallerService;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FuenteDinamica implements IFuenteDinamica {
  private final WebApiCallerService webApiCallerService;
  @Value("${api.servicioUsuarios.url}")
  private  String baseUsuarioUrl;

  public FuenteDinamica(WebApiCallerService webApiCallerService) {
    this.webApiCallerService = webApiCallerService;
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

  public HechoDTOOutput crearHecho(HechoDTOInput hecho, String baseUrl) {
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("contenidomultimedia", hecho.getContenidoMultimediaFile().getResource());
      builder.part("hecho",hecho.getMultipart());

    return webApiCallerService.postMultipart(baseUsuarioUrl + "/api/fuenteDinamica/hechos?baseUrl=" + baseUrl, builder, HechoDTOOutput.class );
  }

  public HechoDTOOutput actualizarHecho(HechoDTOInput hecho, String baseUrl) {
    return this.webApiCallerService.put(baseUsuarioUrl + "/api/fuenteDinamica/hechos/" + hecho.getId() +"?baseUrl=" + baseUrl, hecho, HechoDTOOutput.class);
  }

  public HechoDTOOutput revisarHecho(Long idHecho, RevisionDTO revisionDTO, String baseUrl) {
    return this.webApiCallerService.post(baseUsuarioUrl + "/api/fuenteDinamica/hechos/" + idHecho +"/revisados?baseUrl="+ baseUrl, revisionDTO, HechoDTOOutput.class);
  }

  public SolicitudEdicionDTO solicitarModificacion(HechoDTOInput hechoDtoInput, Long userId, String baseUrl) {
    // Creamos la solicitud
    SolicitudEdicionDTO solicitud = new SolicitudEdicionDTO();
    solicitud.setIdHecho(hechoDtoInput.getId());
    solicitud.setEstado("PENDIENTE");
    solicitud.setFechaSolicitud(LocalDateTime.now());
    solicitud.setPropuesta(hechoDtoInput.getMultipart());
    solicitud.setIdusuario(userId);
    solicitud.setJustificacion("Cambios en el Hecho");
    MultipartBodyBuilder builder = new MultipartBodyBuilder();
    builder.part("contenidomultimedia", hechoDtoInput.getContenidoMultimediaFile().getResource());
    builder.part("solicitud", solicitud);
    return this.webApiCallerService.postMultipart(baseUsuarioUrl + "/api/fuenteDinamica/solicitudes?baseUrl=" + baseUrl, builder, SolicitudEdicionDTO.class);
  }

  public SolicitudEdicionDTO  procesarSolicitudEdicion(Long idSolicitud, String baseUrl, RevisionDTO revisionDTO) {
    return this.webApiCallerService.put(baseUsuarioUrl + "/api/fuenteDinamica/solicitudes/"+ idSolicitud + "?baseUrl="  + baseUrl, revisionDTO, SolicitudEdicionDTO.class);
  }

  @Override
  public List<SolicitudEdicionDTO> findAllSolicitudesEdicion(String baseUrl) {
    return this.webApiCallerService.get(
            baseUsuarioUrl + "/api/fuenteDinamica/solicitudes" + "?baseUrl=" + baseUrl,
            ConjuntoSolicitudesEdicionOutput.class
    ).getSolicitudes();
  }

  @Override
  public List<HechoDTOOutput> listHechosDelUsuario(Long userId, String baseUrl) {
    return this.webApiCallerService.get(
            this.baseUsuarioUrl + "/api/fuenteDinamica/usuarios/"+ userId +"/hechos?baseUrl=" + baseUrl, ConjuntoHechoDTO.class).getHechos();
  }

  @Override
  public HechoDTOOutput getHecho(Long idHecho, String urlFuenteDinamica) {
    return this.webApiCallerService.get(
            this.baseUsuarioUrl + "/api/fuenteDinamica/hechos/"+ idHecho +"?baseUrl=" + urlFuenteDinamica, HechoDTOOutput.class);
  }

  @Override
  public Collection<String> findAllCategorias(String urlFuenteDinamica) {
    return this.webApiCallerService.get(
            this.baseUsuarioUrl + "/api/fuenteDinamica/categorias?baseUrl=" + urlFuenteDinamica, ConjuntoCategorias.class).getCategorias();
  }

  @Override
  public List<HechoDTOOutput> findHechosNuevos(String baseUrl, String estado, Integer nroPagina) {
    String url = this.baseUsuarioUrl + "/api/fuenteDinamica/nuevos-hechos" + "?baseUrl=" + baseUrl + "&estado=" + estado + "&nroPagina=" + nroPagina;
    return this.webApiCallerService.get(url, ConjuntoHechoDTO.class).getHechos();
  }
}
