package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import java.util.List;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISolicitudRepository {
  Solicitud save(Solicitud solicitud);
  Optional<Solicitud> findById(Long id);

  List<Solicitud> findByIDHecho(Long id);
}
