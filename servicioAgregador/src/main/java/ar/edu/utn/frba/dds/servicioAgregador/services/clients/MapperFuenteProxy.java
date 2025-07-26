package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoProxy;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTOProxy;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.TipoOrigen;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;

public class MapperFuenteProxy extends MapperFuenteClient {

  @Override
  public List<Hecho> toHechos(WebClient.ResponseSpec respuesta, String url) {
    return respuesta.bodyToMono(ConjuntoHechoProxy.class).map(
            response -> response.getHechos().stream().map(hecho -> this.mapearHecho(hecho, url)).toList()).block();
  }

  private Hecho mapearHecho(HechoDTOProxy hechoDTO, String url) {
    return this.crearHechoBasico(hechoDTO)
            .origen(new Origen(TipoOrigen.PROXY, url))
            .build();
  }

}
