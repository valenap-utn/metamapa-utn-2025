package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import java.util.List;

public interface ICategoriaRepository {
  List<Categoria> findByFullTextSearch(String nombreCategoria);

  Categoria save(Categoria categoria);
}
