package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class APIFuenteProxyClient extends APIFuenteClient{
  APIFuenteProxyClient(String baseUrl) {
    super(baseUrl);
  }

  @Override
  protected Mono<Fuente> mapAFuenteConHechos(WebClient.ResponseSpec retrieve, Fuente fuente) {
    return retrieve.bodyToMono(ConjuntoHechoCompleto.class).map(
            response -> {
              return this.cargarHechosMapeadosEnFuente(response, fuente);
            });
  }

  @Override
  protected Hecho completarHecho(Hecho hecho, HechoDTO hechoDTO) {
    hecho.setOrigen(Origen.PROXY);
    return hecho;
  }
}
