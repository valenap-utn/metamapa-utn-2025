package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISolicitudRepositoryJPA extends JpaRepository<Solicitud, Long> {
}
