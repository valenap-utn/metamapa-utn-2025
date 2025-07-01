package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IHechoRepository {

  public List<Hecho> findAll();
  public Hecho findById(Long id);

  public void saveHecho(Hecho hecho);
}
