package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.FuenteProxy;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.util.List;

public interface IFuenteProxyRepository {
  public List<FuenteProxy> findAll();
  public FuenteProxy findByOrigen(Origen origen);
  public void save(Origen origen, FuenteProxy fuente);
}
