package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IColeccionRepository;
import org.springframework.stereotype.Service;

@Service
public class ColeccionService implements IColeccionService{
  private IColeccionRepository coleccionRepository;

  public ColeccionService(IColeccionRepository coleccionRepository) {
    this.coleccionRepository = coleccionRepository;
  }
}
