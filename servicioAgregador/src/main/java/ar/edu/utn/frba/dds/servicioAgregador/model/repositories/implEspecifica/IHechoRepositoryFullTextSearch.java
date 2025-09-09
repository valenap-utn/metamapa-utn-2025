package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implEspecifica;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.util.List;
import org.springframework.data.jpa.repository.NativeQuery;

public interface IHechoRepositoryFullTextSearch {
  List<Hecho> findByFullTextSearch(String titulo);
}
