package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.util.List;

public interface IFuenteEstaticaDinamicaRepository {
  public void save(Origen origen, Fuente fuente);
  public Fuente findByOrigen(Origen origen);
  public List<Fuente> findAll();
}
