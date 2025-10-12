package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.TipoOrigen;
import jakarta.validation.constraints.Max;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IOrigenRepositoryJPA extends JpaRepository<Origen, Long> {
  @Query("SELECT o FROM Origen o WHERE o.url = :url AND o.tipo = :tipo AND o.nombreAPI = :clientNombre")
  List<Origen> findByUrlAndTipoAndClientNombre(@Param("url") String url, @Param("tipo") TipoOrigen tipo, @Param("clientNombre") String clientNombre);

  List<Origen> findByUrlAndTipo(String url, TipoOrigen tipo);
}
