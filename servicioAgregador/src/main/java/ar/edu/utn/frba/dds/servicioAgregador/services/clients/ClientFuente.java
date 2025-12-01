package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.ErrorAPIGobierno;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ErrorConexionFuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.net.URI;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

public class ClientFuente {
  @Getter
  private final MapperFuenteClient mapper;
  private final String baseUrl;

  public ClientFuente(String baseUrl, MapperFuenteClient mapper) {
    this.mapper = mapper;
    this.baseUrl = baseUrl;
  }

  private WebClient initWebClient() {
    return WebClient.builder().baseUrl(baseUrl)
            .exchangeStrategies(ExchangeStrategies
                    .builder()
                    .codecs(codecs -> codecs
                            .defaultCodecs()
                            .maxInMemorySize(50 * 1024 * 1024))
                    .build()).build();
  }


  public List<Hecho> getHechos(FiltroDTO filtro) {
    WebClient.ResponseSpec respuesta = this.initWebClient().get()
            .uri(uriBuilder -> this.uriSegunSusFiltros(uriBuilder, filtro) )
            .retrieve();
    return this.mapper.toHechos(respuesta, this.baseUrl);
  }

  private URI uriSegunSusFiltros(UriBuilder uriBuilder, FiltroDTO filtroDTO) {
    UriBuilder uriAMedioConstruir = uriBuilder.path("/api/hechos");
    uriAMedioConstruir.queryParam("servicioAgregador", Boolean.TRUE);
    if(filtroDTO != null) {
      uriAMedioConstruir.queryParam("categoria", filtroDTO.getCategoria())
              .queryParam("fecha_reporte_desde", filtroDTO.getFecha_reporte_desde())
              .queryParam("fecha_reporte_hasta", filtroDTO.getFecha_reporte_hasta())
              .queryParam("fecha_acontecimiento_desde", filtroDTO.getFecha_acontecimiento_desde())
              .queryParam("fecha_acontecimiento_hasta", filtroDTO.getFecha_acontecimiento_hasta())
              .queryParam("latitud", filtroDTO.getLatitud())
              .queryParam("longitud", filtroDTO.getLongitud());
    }
    return uriAMedioConstruir.build();
  }


  public void postEliminado(Hecho hecho, Long idHecho) {
    WebClient.ResponseSpec responseDelete =this.initWebClient().delete().uri(uriBuilder -> this.construirDeleteHecho(uriBuilder, idHecho, hecho))
            .retrieve();
    this.mapper.toHecho(responseDelete, this.baseUrl);
  }

  private URI construirDeleteHecho(UriBuilder uriBuilder, Long idHecho, Hecho hecho) {
    UriBuilder uriAMedioConstruir = uriBuilder.path("/api/hechos/{id}");
    this.mapper.modificarHechoParaEliminacion(hecho, uriAMedioConstruir);
    return uriAMedioConstruir.build(idHecho);
  }


}
