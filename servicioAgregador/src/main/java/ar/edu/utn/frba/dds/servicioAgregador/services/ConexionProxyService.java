package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ConexionProxyService extends ConexionFuenteService{

  ConexionProxyService(String baseUrl) {
    super(baseUrl);
  }

  @Override
  public void cargarHechosEnFuente(Fuente fuente, IHechoRepository _hechoRepository) {
    Mono<Fuente> fuenteConEspera =  this.setFuenteConHechosAPI(fuente);
    fuenteConEspera.block();
  }

  @Override
  public Mono<Void> actualizarHechosFuente(Fuente _fuente, IHechoRepository _hechoRepository) {
    return Mono.empty();
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
