package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;

public interface IColeccionRepository {
  public Coleccion crearColeccion();
  public Coleccion findById(Long id);
}
