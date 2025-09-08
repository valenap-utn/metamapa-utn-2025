package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.comparaciones.ComparacionHechos;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VerificadorNormalizador {
  private final List<ComparacionHechos> comparacionesAHacer;
  
  public VerificadorNormalizador() {
    this.comparacionesAHacer = new ArrayList<>();
  }

  public boolean estaNormalizado(Hecho hecho, Hecho hechoExterno) {
    return this.comparacionesAHacer.stream().allMatch(comparacionHecho -> comparacionHecho.sonEquivalentes(hecho, hechoExterno));
  }
}
