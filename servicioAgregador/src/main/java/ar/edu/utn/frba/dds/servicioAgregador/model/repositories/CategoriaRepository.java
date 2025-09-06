package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class CategoriaRepository implements ICategoriaRepository{
  private final AtomicLong idGenerator = new AtomicLong(1);
  private final Map<Long, Categoria> ids;

  public CategoriaRepository() {
    this.ids = new HashMap<>();
  }

  public Categoria save(Categoria categoria) {
    Long id = categoria.getId();
    if(id == null) {
      id = idGenerator.getAndIncrement();
      categoria.setId(id);
    }
    this.ids.put(id, categoria);
    return categoria;
  }
  @Override
  public List<Categoria> findByFullTextSearch(String nombreCategoria) {
    return List.of();
  }
}
