package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.APIFuenteClient;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.util.List;

public class FuenteEstaticaDinamica extends Fuente {
  @Getter private final APIFuenteClient apiClient;

  public FuenteEstaticaDinamica(Origen origen, APIFuenteClient apiClient) {
    super(origen);
    this.apiClient = apiClient;
  }

  public Mono<Void> cargarHechosEnFuente(List<Hecho> hechos) {
    this.actualizarHechos(hechos);
    return Mono.empty();
  }

  public Mono<Fuente> actualizarHechosFuente() {
    return this.apiClient.setFuenteConHechosAPI(this);
  }

  @Override
  public void postEliminado(Hecho hecho, Long idHecho) {
    apiClient.postEliminado(hecho, idHecho);
  }

}
