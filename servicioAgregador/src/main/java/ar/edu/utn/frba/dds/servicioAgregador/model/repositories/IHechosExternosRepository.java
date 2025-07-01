package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

public interface IHechosExternosRepository {
  public void save(Long idFuente,Long idAgregador);

  public Long findIDFuente(Long idAgregador);
}
