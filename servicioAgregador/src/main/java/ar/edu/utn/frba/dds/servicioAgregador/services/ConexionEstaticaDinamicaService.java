package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.APIFuenteClient;
import lombok.Getter;
import reactor.core.publisher.Mono;

public class ConexionEstaticaDinamicaService implements ConexionFuenteService {
  @Getter
  private final APIFuenteClient apiClient;

  public Mono<Void> cargarHechosEnFuente(Fuente fuente, IHechoRepository hechoRepository) {
    return Mono.fromCallable(() -> hechoRepository.findByIDFuente(fuente.getId()))
            .map( hechos -> {
              fuente.actualizarHechos(hechos);
              return Mono.empty();
            }).then();
  }
  public Mono<Void> actualizarHechosFuente(Fuente fuente, IHechoRepository hechoRepository) {
    Mono<Fuente> fuenteActualizada = this.apiClient.setFuenteConHechosAPI(fuente);
    return fuenteActualizada.map(fuenteMono -> {
      hechoRepository.saveHechosDeFuente(fuenteMono);
      return Mono.empty();
    }).then();
  }

  ConexionEstaticaDinamicaService(APIFuenteClient apiClient) {
    this.apiClient = apiClient;
  }
}
