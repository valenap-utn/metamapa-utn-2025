package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import reactor.core.publisher.Mono;

public interface ConexionFuenteService {
  public Mono<Void> cargarHechosEnFuente(Fuente fuente, IHechoRepository hechoRepository);
  public Mono<Void> actualizarHechosFuente(Fuente fuente, IHechoRepository hechoRepository);
}
