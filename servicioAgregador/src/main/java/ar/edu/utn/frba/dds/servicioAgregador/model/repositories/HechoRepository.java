package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class HechoRepository implements IHechoRepository {
  private final Map<Long, Map<Long, Hecho>> idsHechosPorFuente;

  public HechoRepository() {
    this.idsHechosPorFuente = new HashMap<>();
  }

  @Override
  public Fuente saveHechosDeFuente(Fuente fuente) {
    Map<Long, Hecho> idsHechoFuente = this.idsHechosPorFuente.get(fuente.getId());
    if (idsHechoFuente == null) {
      idsHechoFuente = new HashMap<>();
    }
    Map<Long, Hecho> finalIdsHechoFuente = idsHechoFuente;
    fuente.getHechos().forEach(hecho -> this.saveHecho(finalIdsHechoFuente, hecho));
    return fuente;
  }

  private void saveHecho(Map<Long, Hecho> idsHechoFuente, Hecho hecho) {
    idsHechoFuente.put(hecho.getId(), hecho);
  }


  @Override
  public Fuente findById(Long id) {
    return null;
  }

  @Override
  public Set<Fuente> findAll() {
    return Set.of();
  }

  @Override
  public Set<Hecho> findByIDFuente(Long idFuente) {
    Map<Long, Hecho> idsHechoFuente = this.idsHechosPorFuente.get(idFuente);
    return idsHechoFuente.values().stream().collect(Collectors.toSet());
  }
}
