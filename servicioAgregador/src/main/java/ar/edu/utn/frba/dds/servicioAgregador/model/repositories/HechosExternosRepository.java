package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;


import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class HechosExternosRepository implements IHechosExternosRepository{
  private final Map<Long, Long> idsHechos;

  public HechosExternosRepository() {
    this.idsHechos = new HashMap<>();
  }


  public void save(Long idFuente,Long idAgregador) {
    this.idsHechos.put(idAgregador, idFuente);
  }

  public Long findIDFuente(Long idAgregador) {
    return this.idsHechos.get(idAgregador);
  }
}
