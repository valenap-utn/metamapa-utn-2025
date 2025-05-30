package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoDinamica;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class APIFuenteDinamicaClient extends APIFuenteClient{
  APIFuenteDinamicaClient(String baseUrl) {
    super(baseUrl);
  }

  @Override
  protected Mono<Fuente> mapAFuenteConHechos(WebClient.ResponseSpec retrieve, Fuente fuente) {
    return retrieve.bodyToMono(ConjuntoHechoDinamica.class).map(
            response -> this.cargarHechosMapeadosEnFuente(response, fuente));
  }

  @Override
  protected <R> Hecho completarHecho(Hecho hecho, HechoDTO<R> hechoDTO) {
    hecho.setOrigen(Origen.PORCONTRIBUYENTE);
    return hecho;
  }
}
