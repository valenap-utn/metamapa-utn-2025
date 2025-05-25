package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;
import java.util.Set;

public interface ISolicitudRepository {
  public Solicitud save(Solicitud solicitud);
  public Solicitud findById(Long id);

  Set<Solicitud> findAll();
}
