package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoEstatica;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTOEstatica;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.TipoOrigen;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class MapperFuenteEstatica extends MapperFuenteClient {


  @Override
  public List<Hecho> toHechos(WebClient.ResponseSpec respuesta, String url) {
    return respuesta.bodyToMono(ConjuntoHechoEstatica.class).map(
            response -> response.getHechos().stream().map(hecho -> this.mapearHecho(hecho, url)).toList())
            .onErrorResume( e -> {
              System.out.println("Hubo un error en la comunicación con la fuente de url" + url + " debido a " + e.getMessage());
              return Mono.just(new ArrayList<>());
                    }
            ).block();
  }

  @Override
  public Hecho toHecho(WebClient.ResponseSpec responseDelete, String url) {
    return responseDelete.bodyToMono(HechoDTOEstatica.class).map(
            hecho -> this.mapearHecho(hecho, url)
    ).onErrorResume( e -> {
              System.out.println("Hubo un error en la comunicación con la fuente de url" + url + " debido a " + e.getMessage());
              return Mono.just(new Hecho());
            }
    ).block();
  }

  private Hecho mapearHecho(HechoDTOEstatica hechoDTO, String url) {
    return this.crearHechoBasico(hechoDTO)
            .origen(Origen.builder().tipo(TipoOrigen.DATASET).url(url).build())
            .build();
  }
}
