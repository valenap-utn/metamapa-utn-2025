package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;

public interface IFuenteRepository {
  void save(Origen origen, Fuente fuente);
  Fuente findByOrigen(Origen origen);
}
