package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;

import java.net.ContentHandler;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IHechoRepositoryJPA extends JpaRepository<Hecho, Long> {

  @Query("SELECT h FROM Hecho h WHERE h.estado = :estado1 ")
  Page<Hecho> findHechosByEstado(Pageable pageable, @Param("estado1") Estado estado1);

  @Query("SELECT h FROM Hecho h WHERE h.usuario.id = :idUsuario ")
  Page<Hecho> findHechosByIdUsuario(Pageable pageable, @Param("idUsuario")Long idUsuario);

  @Query("SELECT h FROM Hecho h WHERE h.estado IN :estados")
  Page<Hecho> findHechosByEstadoIn(Pageable pageable, @Param("estados") Collection<Estado> estados);

  @Query("SELECT h FROM Hecho h WHERE h.entregadoAAgregador = :entregadoAgregador")
  Page<Hecho> findAllByEntregadoAAgregador(Pageable pageable, @Param("entregadoAgregador") Boolean entregadoAgregador);
}
