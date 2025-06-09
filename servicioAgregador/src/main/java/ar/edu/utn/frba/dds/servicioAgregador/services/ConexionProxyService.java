package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.APIFuenteProxyClient;
import lombok.Getter;
import reactor.core.publisher.Mono;

public class ConexionProxyService implements ConexionFuenteService{
  @Getter
  private final APIFuenteProxyClient apiClient;

  ConexionProxyService(APIFuenteProxyClient apiClient) {
    this.apiClient = apiClient;
  }

  @Override
  public Mono<Void> cargarHechosEnFuente(Fuente fuente, IHechoRepository _hechoRepository) {
    return this.getApiClient().setFuenteConHechosAPI(fuente)
            .map(fuenteMapeada -> {
              return Mono.empty();
            }).then();
  }

  @Override
  public Mono<Void> actualizarHechosFuente(Fuente _fuente, IHechoRepository _hechoRepository) {
    return Mono.empty();
  }


}
