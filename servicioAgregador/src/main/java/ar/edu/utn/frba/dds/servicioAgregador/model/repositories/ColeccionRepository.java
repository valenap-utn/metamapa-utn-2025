package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ColeccionRepository implements IColeccionRepository {
  private final Map<Long, Coleccion> colecciones;
  private final AtomicLong idGenerator;

  public ColeccionRepository() {
    this.colecciones = new HashMap<>();
    this.idGenerator = new AtomicLong(1);
  }

  @Override
  public Coleccion crearColeccion() {
    return null;
  }

  @Override
  public Coleccion findById(Long id) {
    return null;
  }
}
