package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class HechoRepository implements IHechoRepository {
  private final AtomicLong idGenerator = new AtomicLong(1);
  private final Map<Long, Hecho> idsHechos;

  public HechoRepository() {
    this.idsHechos = new HashMap<>();
  }

  public Hecho saveHecho(Hecho hecho) {
    Long id = hecho.getId();
    if(id == null) {
      id = idGenerator.getAndIncrement();
      hecho.setId(id);
    }
    this.idsHechos.put(id, hecho);
    return hecho;
  }

  @Override
  public List<Hecho> findByOrigen(Origen origen) {
    return this.idsHechos.values().stream().filter(e -> e.getOrigen().equals(origen)).toList();
  }

  @Override
  public List<Hecho> findByOrigenWithFiltros(Origen origen, FiltroDTO filtro) {
    return this.idsHechos.values().stream().filter(hecho -> hecho.getOrigen().equals(origen) && this.cumpleFiltros(hecho, filtro)).toList();
  }

  private boolean cumpleFiltros(Hecho hecho, FiltroDTO filtro) {
    return hecho.getCategoria().equals(filtro.getCategoria());
  }

  @Override
  public Hecho findById(Long id) {
    return this.idsHechos.get(id);
  }

  @Override
  public List<Hecho> findAll(){
    return this.idsHechos.values().stream().toList();
  }
}
