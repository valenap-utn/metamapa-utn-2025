package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implEspecifica;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Direccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

public class DireccionRepositoryMySQL implements IDireccionRepositoryFullTextSearch{
  @PersistenceContext
  private EntityManager entityManager;
  @Value("${api.formato.fulltextsearch.direccion}")
  private String queryFullTextSearch;



  @Override
  public List<Direccion> findByFullTextSearch(Direccion direccion) {
    try {
      TypedQuery<Direccion> query = entityManager.createQuery(this.queryFullTextSearch, Direccion.class);
      query.setParameter("municipio", direccion.getMunicipio());
      query.setParameter("provincia", direccion.getProvincia());
      query.setParameter("departamento", direccion.getDepartamento());
      return query.getResultList();
    } catch (Exception e) {
      throw new RuntimeException("Error al ejecutar consulta desde properties", e);
    }
  }

}
