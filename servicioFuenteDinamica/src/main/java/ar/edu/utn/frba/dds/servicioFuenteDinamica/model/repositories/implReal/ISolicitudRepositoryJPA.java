package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISolicitudRepositoryJPA extends JpaRepository<Solicitud, Long> {
  List<Solicitud> findSolicitudByHecho(Hecho hecho);
}
