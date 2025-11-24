package ar.edu.utn.frba.dds.metamapa_client.clients;

import ar.edu.utn.frba.dds.metamapa_client.dtos.ConjuntoSolicitudesEdicionOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.HechoDTOInput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.SolicitudEdicionDTO;
import ar.edu.utn.frba.dds.metamapa_client.services.internal.WebApiCallerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
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

  public SolicitudEdicionDTO solicitarModificacion(SolicitudEdicionDTO solicitudEdicion, String baseUrl) {
    return this.webApiCallerService.post(baseUsuarioUrl + "/api/fuenteDinamica/solicitudes?baseUrl=" + baseUrl, solicitudEdicion, SolicitudEdicionDTO.class);
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
}
