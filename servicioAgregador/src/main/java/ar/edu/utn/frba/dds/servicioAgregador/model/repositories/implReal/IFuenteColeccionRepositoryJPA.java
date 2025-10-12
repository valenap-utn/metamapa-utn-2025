package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.FuenteColeccion;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IFuenteColeccionRepositoryJPA extends JpaRepository<FuenteColeccion, Long> {
  @NativeQuery("SELECT * FROM fuente_coleccion fc WHERE fc.coleccion_id = :id ")
  List<FuenteColeccion> findByIdColeccion(@Param("id") UUID id);

}
