package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Direccion;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionRepository implements IDireccionRepository{
  @Override
  public List<Direccion> findByFullTextSearch(Direccion nombreCategoria) {
    return List.of();
  }
}
