package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Direccion;
import reactor.core.publisher.Mono;

public interface IDireccionService {

  Mono<Direccion> actualizarDirecciones();
}
