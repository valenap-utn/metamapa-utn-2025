package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IHechoRepositoryJPA extends JpaRepository<Hecho, Long> {

  @Query("SELECT h FROM Hecho h WHERE h.estado = :estado1 ")
  List<Hecho> findHechosByEstado(@Param("estado1") Estado estado1);

  @Query("SELECT h FROM Hecho h WHERE h.usuario.id = :idUsuario ")
  List<Hecho> findHechosByIdUsuario(@Param("idUsuario")Long idUsuario);

  @Query("SELECT h FROM Hecho h WHERE h.estado IN :estados")
  List<Hecho> findHechosByEstadoIn(@Param("estados") Collection<Estado> estados);
}
