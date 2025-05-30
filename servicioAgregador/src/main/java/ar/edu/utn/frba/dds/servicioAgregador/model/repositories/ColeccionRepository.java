package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ColeccionRepository implements IColeccionRepository {
  private final Map<Long, Coleccion> colecciones;
  private final Map<String, Long> coleccionIds;
  private final AtomicLong idGenerator;

  public ColeccionRepository() {
    this.colecciones = new HashMap<>();
    this.coleccionIds = new HashMap<>();
    this.idGenerator = new AtomicLong(1);
  }

  @Override
  public Coleccion save(Coleccion coleccion) {
    if(coleccion.getId() == null){
      Long id = this.idGenerator.getAndIncrement();
      coleccion.setId(id.toString());
      this.colecciones.put(id, coleccion);
      this.coleccionIds.put(coleccion.getId(), id);
    } else {
      Long id = this.coleccionIds.get(coleccion.getId());
      this.colecciones.put(id, coleccion);
    }
    return coleccion;
  }

  @Override
  public Coleccion findById(String id) {
    Long idColeccion = this.coleccionIds.get(id);
    return this.colecciones.get(idColeccion);
  }

  @Override
  public Set<Coleccion> findAll() {
    return new HashSet<>(this.colecciones.values());
  }
}
