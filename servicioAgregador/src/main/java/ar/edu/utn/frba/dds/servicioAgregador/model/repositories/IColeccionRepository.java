package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IColeccionRepository {
  public Coleccion save(Coleccion coleccion);
  public Coleccion findById(UUID id);

  Set<Coleccion> findAll();
}
