package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public interface IHechosExternosRepository {
  public Hecho save(Hecho hecho);

  public Long findIDFuente(Long idAgregador);
}
