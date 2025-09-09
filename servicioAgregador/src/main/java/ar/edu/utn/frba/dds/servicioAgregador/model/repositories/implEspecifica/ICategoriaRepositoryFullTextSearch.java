package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implEspecifica;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import java.util.List;

public interface ICategoriaRepositoryFullTextSearch {
  List<Categoria> findByFullTextSearch(String nombreCategoria);
}
