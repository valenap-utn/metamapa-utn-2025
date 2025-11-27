package ar.edu.utn.frba.dds.services.servicioAgregador;

import ar.edu.utn.frba.dds.model.dtos.ColeccionDTO;
import ar.edu.utn.frba.dds.model.dtos.ConjuntoColeccionDTO;
import ar.edu.utn.frba.dds.model.dtos.ConjuntoHechoAgregador;
import ar.edu.utn.frba.dds.model.dtos.ConjuntoSolicitudesAgregador;
import ar.edu.utn.frba.dds.model.dtos.HechoDTO;
import ar.edu.utn.frba.dds.model.dtos.SolicitudDTO;
import ar.edu.utn.frba.dds.model.entities.Estado;
import ar.edu.utn.frba.dds.model.entities.Hecho;
import ar.edu.utn.frba.dds.model.entities.Solicitud;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientServicioAgregador implements ServicioAgregador {
  private final WebClient webClient;

  public ClientServicioAgregador(@Value("${api.url.servicioAgregador}") String baseUrl) {
    this.webClient = WebClient.builder().baseUrl(baseUrl)
            .exchangeStrategies(ExchangeStrategies
            .builder()
            .codecs(codecs -> codecs
                    .defaultCodecs()
                    .maxInMemorySize(50 * 1024 * 1024))
            .build()).build();
  }


  @Override
  public List<Hecho> obtenerHechos() {
    return webClient.get().uri(uriBuilder -> uriBuilder.path("/api/hechos").build())
            .retrieve()
            .bodyToMono(ConjuntoHechoAgregador.class)
            .map( conjuntoHechoAgregador -> {
                      List<HechoDTO> hechos = conjuntoHechoAgregador.getHechos();
                      return hechos.stream().map(this::toHecho).toList();
                    }
            )
            .block();
  }

  private Hecho toHecho(HechoDTO hechoDTO) {
    return Hecho.builder().idUsuario(hechoDTO.getIdUsuario())
            .descripcion(hechoDTO.getDescripcion())
            .id(hechoDTO.getId()).fechaCarga(hechoDTO.getFechaCarga())
            .fechaAcontecimiento(hechoDTO.getFechaAcontecimiento())
            .ubicacion(hechoDTO.getUbicacion())
            .categoria(hechoDTO.getCategoria())
            .contenidoMultimedia(hechoDTO.getContenidoMultimedia())
            .etiquetas(hechoDTO.getEtiquetas())
            .origen(hechoDTO.getOrigen())
            .titulo(hechoDTO.getTitulo()).build();
  }

  @Override
  public List<Solicitud> obtenerSolicitudes() {
    return webClient.get().uri(uriBuilder -> uriBuilder.path("/api/solicitudes").build())
            .retrieve()
            .bodyToMono(ConjuntoSolicitudesAgregador.class)
            .map( conjuntoSolicitudesAgregador -> {
                      List<SolicitudDTO> solicitudes  = conjuntoSolicitudesAgregador.getSolicitudes();
                      
                      return solicitudes.stream().map(this::toSolicitud).toList();
                    }
            )
            .block();
  }

  private Solicitud toSolicitud(SolicitudDTO solicitudDTO) {
    Solicitud solicitud = new Solicitud();
    solicitud.setEstado(Estado.valueOf(solicitudDTO.getEstado()));
    solicitud.setIdHecho(solicitudDTO.getIdHecho());
    solicitud.setJustificacion(solicitudDTO.getJustificacion());
    solicitud.setId(solicitudDTO.getId());
    solicitud.setIdUsuario(solicitudDTO.getIdusuario());
    return solicitud;
  }

  @Override
  public List<ColeccionDTO> obtenerColecciones() {
    return webClient.get().uri(uriBuilder -> uriBuilder.path("/api/colecciones").build())
            .retrieve()
            .bodyToMono(ConjuntoColeccionDTO.class)
            .map( ConjuntoColeccionDTO::getColecciones)
            .block();
  }
}
