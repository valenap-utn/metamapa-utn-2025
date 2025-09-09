package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Direccion;
import java.util.List;

public interface IDireccionRepository {
  List<Direccion> findByFullTextSearch(Direccion nombreCategoria);
}
