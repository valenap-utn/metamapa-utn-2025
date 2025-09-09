package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implEspecifica;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Direccion;
import java.util.List;

public interface IDireccionRepositoryFullTextSearch {
  List<Direccion> findByFullTextSearch(Direccion direccion);
}
