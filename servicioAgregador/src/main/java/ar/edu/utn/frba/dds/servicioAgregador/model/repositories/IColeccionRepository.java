package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import java.util.List;
import java.util.Set;

public interface IColeccionRepository {
  public Coleccion crearColeccion(Coleccion coleccion);
  public Coleccion findById(Long id);

  Set<Coleccion> findAll();
}
