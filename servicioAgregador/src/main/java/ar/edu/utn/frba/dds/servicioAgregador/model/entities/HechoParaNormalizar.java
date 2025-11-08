package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import java.util.List;
import lombok.Data;

@Data
public class HechoParaNormalizar {
  private Hecho hecho;
  private List<Hecho> hechosCopia;
  public HechoParaNormalizar(Hecho hecho, List<Hecho> hechos) {
    this.hecho = hecho;
    this.hechosCopia = hechos;
  }
}
