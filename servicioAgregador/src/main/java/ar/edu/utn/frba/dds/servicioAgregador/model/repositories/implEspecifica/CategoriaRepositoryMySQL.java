package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implEspecifica;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class CategoriaRepositoryMySQL implements ICategoriaRepositoryFullTextSearch{
  @PersistenceContext
  private EntityManager entityManager;
  @Value("${api.formato.fulltextsearch.categoria}")
  private String queryFullTextSearch;



  @Override
  public List<Categoria> findByFullTextSearch(String nombre) {
    try {
      Query query = entityManager.createNativeQuery(this.queryFullTextSearch, Categoria.class);
      query.setParameter("nombre", nombre);
      return query.getResultList();
    } catch (Exception e) {
      throw new RuntimeException("Error al ejecutar consulta desde properties", e);
    }
  }
}
