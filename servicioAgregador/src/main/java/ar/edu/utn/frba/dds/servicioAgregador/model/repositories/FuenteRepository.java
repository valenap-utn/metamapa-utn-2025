package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.services.ConexionFuenteService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class FuenteRepository implements IFuenteRepository {
  private final Map<Origen, Fuente> fuentes;

  public FuenteRepository() {
    this.fuentes = new HashMap<>();
  }

  public void save(Origen origen, Fuente fuente) {
    if(!this.fuentes.containsKey(origen)){
      this.fuentes.put(origen, fuente);
    }
  }

  public Fuente findByOrigen(Origen origen) {
    return this.fuentes.get(origen);
  }

  @Override
  public List<Fuente> findAll() {
    return this.fuentes.values().stream().toList();
  }
}
