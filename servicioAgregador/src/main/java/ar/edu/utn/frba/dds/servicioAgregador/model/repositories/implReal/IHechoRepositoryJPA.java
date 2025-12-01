package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.TipoOrigen;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IHechoRepositoryJPA extends JpaRepository<Hecho, Long>, JpaSpecificationExecutor<Hecho> {
  @Query("select h FROM Hecho h LEFT JOIN FETCH h.algosAceptados JOIN h.origen o WHERE o.url = :url AND o.tipo = :tipo")
  List<Hecho> findByOrigen(@Param("url") String url, @Param("tipo") TipoOrigen tipo);

  @Query("select h FROM Hecho h WHERE h.idExterno = :idExterno1 AND h.origen.id = :idOrigen")
  Hecho findByIdExternoAndOrigen(@Param("idExterno1") Long idExterno,@Param("idOrigen") Long idOrigen);

  List<Hecho> findByNormalizado(Pageable pageRequest, boolean b);

  List<Hecho> findAllByEliminado(Boolean aFalse);

  @Query("SELECT h FROM Hecho h WHERE h.idExterno IN :idsExternos AND h.origen.id IN :idsOrigenes")
  List<Hecho> findAllByIdsExternoAndOrigenes(@Param("idsExternos") List<Long> list,@Param("idsOrigenes") List<Long> list1);

}
