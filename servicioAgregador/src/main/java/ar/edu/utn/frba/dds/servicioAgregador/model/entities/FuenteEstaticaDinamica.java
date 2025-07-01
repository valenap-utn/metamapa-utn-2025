package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.APIFuenteClient;
import java.time.LocalDate;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.util.List;

public class FuenteEstaticaDinamica extends Fuente {
  @Getter private final APIFuenteClient apiClient;

  public FuenteEstaticaDinamica(Origen origen, APIFuenteClient apiClient) {
    super(origen);
    this.apiClient = apiClient;
  }



  public Mono<Void> actualizarHechosFuente() {
    return this.apiClient.setFuenteConHechosAPI(this).map(fuente -> {return Mono.empty();}).then();
  }

  @Override
  public void postEliminado(Hecho hecho, Long idHecho) {
    apiClient.postEliminado(hecho, idHecho);
  }

}
