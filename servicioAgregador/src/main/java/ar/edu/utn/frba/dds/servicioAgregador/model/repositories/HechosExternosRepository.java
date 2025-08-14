package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class HechosExternosRepository implements IHechosExternosRepository{
  private final Map<Long, Long> idsHechos;

  public HechosExternosRepository() {
    this.idsHechos = new HashMap<>();
  }


  public Hecho save(Hecho hecho) {

    this.idsHechos.put(hecho.getId(), hecho.getIdExterno());
    return hecho;
  }

  public Long findIDFuente(Long idAgregador) {
    return this.idsHechos.get(idAgregador);
  }
}
