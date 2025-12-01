package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import java.net.ContentHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IHechoRepositoryJPA extends JpaRepository<Hecho, Long> {
  Hecho findHechoById(Long id);

  @Query("SELECT h FROM Hecho h WHERE h.entregadoAAgregador = :entregadoAgregador AND h.eliminado = :eliminado1")
  Page<Hecho> findAllByEntregadoAAgregador(Pageable pageable,@Param("entregadoAgregador") Boolean estadoEntregado, @Param("eliminado1") Boolean eliminado);

  @Query("SELECT h FROM Hecho h WHERE h.eliminado = :eliminado1")
  Page<Hecho> findAll(Pageable pageable,@Param("eliminado1") Boolean eliminado);
}
