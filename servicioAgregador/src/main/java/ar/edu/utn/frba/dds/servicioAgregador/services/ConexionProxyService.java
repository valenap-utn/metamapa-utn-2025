package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoProxy;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ConexionProxyService extends ConexionFuenteService{

  ConexionProxyService(String baseUrl, IHechoRepository hechoRepository) {
    super(baseUrl, hechoRepository);
  }

  @Override
  public void cargarHechosEnFuente(Fuente fuente) {
    Mono<Fuente> fuenteConEspera =  this.setFuenteConHechosAPI(fuente);
    fuenteConEspera.block();
  }

  @Override
  public Mono<Void> actualizarHechosFuente(Fuente _fuente) {
    return Mono.empty();
  }

  @Override
  protected Mono<Fuente> mapAFuenteConHechos(WebClient.ResponseSpec retrieve, Fuente fuente) {
    return retrieve.bodyToMono(ConjuntoHechoProxy.class).map(
            response -> {
              Set<Hecho> hechos = response.getHechos().stream().map(this::toHecho).collect(Collectors.toSet());
              fuente.actualizarHechos(hechos);
              return fuente;
            });
  }

  @Override
  protected Hecho completarHecho(Hecho hecho, HechoDTO hechoDTO) {
    hecho.setOrigen(Origen.PROXY);
    return hecho;
  }


}
