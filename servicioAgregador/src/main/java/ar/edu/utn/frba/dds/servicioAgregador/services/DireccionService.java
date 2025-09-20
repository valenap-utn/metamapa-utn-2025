package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Direccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.DireccionRepository;
import reactor.core.publisher.Mono;

public class DireccionService implements IDireccionService{
  private DireccionRepository direccionRepository;
  @Override
  public Mono<Direccion> actualizarDirecciones() {

    return null;
  }
}
