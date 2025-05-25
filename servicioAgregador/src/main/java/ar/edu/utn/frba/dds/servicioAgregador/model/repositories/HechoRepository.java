package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import java.util.Set;

public class HechoRepository implements IHechoRepository {

  @Override
  public Fuente save(Fuente fuente) {
    return null;
  }

  @Override
  public Fuente findById(Long id) {
    return null;
  }

  @Override
  public Set<Fuente> findAll() {
    return Set.of();
  }
}
