package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.FuenteProxy;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class FuenteProxyRepository implements IFuenteProxyRepository {
  private final Map<Origen, FuenteProxy> fuentes;

  public FuenteProxyRepository() {
    this.fuentes = new HashMap<>();
  }

  public void save(Origen origen, FuenteProxy fuente) {
    if(!this.fuentes.containsKey(origen)){
      this.fuentes.put(origen, fuente);
    }
  }

  public FuenteProxy findByOrigen(Origen origen) {
    return this.fuentes.get(origen);
  }

  @Override
  public List<FuenteProxy> findAll() {
    return this.fuentes.values().stream().toList();
  }
}
