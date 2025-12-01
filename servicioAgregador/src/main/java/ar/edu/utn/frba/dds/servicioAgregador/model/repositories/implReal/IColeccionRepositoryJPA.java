package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface IColeccionRepositoryJPA extends JpaRepository<Coleccion, UUID> {
  @Query("SELECT c FROM Coleccion c WHERE c.eliminada = :estadoEliminada")
  List<Coleccion> findAllByEliminada(@Param("estadoEliminada")Boolean aFalse);

  @Query("SELECT c FROM Coleccion c JOIN FETCH c.fuenteColeccions")
  List<Coleccion> findAllWithFuentes();
}
