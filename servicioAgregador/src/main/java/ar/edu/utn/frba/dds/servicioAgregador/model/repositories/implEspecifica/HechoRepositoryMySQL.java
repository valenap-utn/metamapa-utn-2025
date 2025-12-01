package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implEspecifica;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class HechoRepositoryMySQL implements IHechoRepositoryFullTextSearch {
  @PersistenceContext
  private EntityManager entityManager;
  @Value("${api.formato.fulltextsearch.titulo}")
  private String queryFullTextSearch;



  @Override
  public List<String> findByFullTextSearch(String titulo) {
    try {
      Query query = entityManager.createNativeQuery(this.queryFullTextSearch, String.class);
      query.setParameter("titulo", titulo);
      return  query.getResultList();
    } catch (Exception e) {
      throw new RuntimeException("Error al ejecutar consulta desde properties " + e.getMessage(), e);
    }
  }
}
