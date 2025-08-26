package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.net.URI;
import java.util.List;
import lombok.Getter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

public class ClientFuente {
  @Getter
  private final MapperFuenteClient mapper;
  private final String baseUrl;

  public ClientFuente(String baseUrl, MapperFuenteClient mapper) {
    this.mapper = mapper;
    this.baseUrl = baseUrl;
  }

  private WebClient initWebClient() {
    return WebClient.builder().baseUrl(baseUrl).build();
  }


  public List<Hecho> getHechos(FiltroDTO filtro) {
    WebClient.ResponseSpec respuesta = this.initWebClient().get()
            .uri(uriBuilder -> this.uriSegunSusFiltros(uriBuilder, filtro) )
            .retrieve();
    return this.mapper.toHechos(respuesta, this.baseUrl);
  }

  private URI uriSegunSusFiltros(UriBuilder uriBuilder, FiltroDTO filtroDTO) {
    UriBuilder uriAMedioConstruir = uriBuilder.path("/api/hechos");
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
    Hecho hechocopia = Hecho.builder().id(idHecho).categoria(hecho.getCategoria())
            .descripcion(hecho.getDescripcion()).fechaAcontecimiento(hecho.getFechaAcontecimiento())
            .eliminado(hecho.isEliminado()).fechaCarga(hecho.getFechaCarga()).build();
    this.initWebClient().patch().uri("/api/hechos/{id}", idHecho);
            //.bodyValue(this.mapHechoOutput.toHechoDTO(hechocopia));

  }

}
