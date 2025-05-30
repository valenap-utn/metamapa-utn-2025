package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoProxy;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapperAPIClient;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class APIFuenteProxyClient extends APIFuenteClient{
  APIFuenteProxyClient(String baseUrl, MapperAPIClient mapperAPIClient) {
    super(baseUrl, mapperAPIClient);
  }

  @Override
  protected Mono<Fuente> mapAFuenteConHechos(WebClient.ResponseSpec retrieve, Fuente fuente) {
    return retrieve.bodyToMono(ConjuntoHechoProxy.class).map(
            response -> {
              Set<Hecho> hechos = response.getHechos().stream().map(this.getMapper()::toHechoFrom).collect(Collectors.toSet());
              return this.cargarHechosMapeadosEnFuente(hechos, fuente);
            });
  }

}
