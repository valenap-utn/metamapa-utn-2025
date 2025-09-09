package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implEspecifica;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.HechoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

@Repository
public class HechoRepositoryMySQL implements IHechoRepositoryFullTextSearch {
  @PersistenceContext
  private EntityManager entityManager;
  @Value("${api.formato.fulltextsearch.titulo}")
  private String queryFullTextSearch;



  @Override
  public List<Hecho> findByFullTextSearch(String titulo) {
    try {
      TypedQuery<Hecho> query = entityManager.createQuery(this.queryFullTextSearch, Hecho.class);
      query.setParameter("titulo", titulo);
      return query.getResultList();
    } catch (Exception e) {
      throw new RuntimeException("Error al ejecutar consulta desde properties", e);
    }
  }
}
