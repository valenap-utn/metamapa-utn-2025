package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public interface IHechosExternosRepository {
  Hecho save(Hecho hecho);

  Long findIDFuente(Long idAgregador);

  Long findByIDExterno(Long id);
}
