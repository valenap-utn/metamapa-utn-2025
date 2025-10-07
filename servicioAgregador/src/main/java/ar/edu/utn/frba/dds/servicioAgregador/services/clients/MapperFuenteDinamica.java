package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoDinamica;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.TipoOrigen;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;

public class MapperFuenteDinamica extends MapperFuenteClient {


  @Override
  public List<Hecho> toHechos(WebClient.ResponseSpec respuesta, String url) {
    return respuesta.bodyToMono(ConjuntoHechoDinamica.class).map(
            response -> response.getHechos().stream().map(hecho -> this.mapearHecho(hecho, url)).toList()).block();
  }

  @Override
  public Hecho toHecho(WebClient.ResponseSpec responseDelete, String url) {
    return responseDelete.bodyToMono(HechoDTODinamica.class).map(
            hecho -> this.mapearHecho(hecho, url)
    ).block();
  }

  private Hecho mapearHecho(HechoDTODinamica hechoDTO, String url) {
    return this.crearHechoBasico(hechoDTO)
            .contenidoMultimedia(hechoDTO.getContenidoMultimedia())
            .origen(Origen.builder().tipo(TipoOrigen.DINAMICA).url(url).build())
            .build();
  }
}
