package ar.edu.utn.frba.dds.servicioAgregador.model.DTOs;

import java.util.Set;
import lombok.Data;

public class ConjuntoHechoEstatica implements ConjuntoHechoDTO{
  Set<HechoDTO> hechos;

  @Override
  public Set<HechoDTO> getHechos() {
    return this.hechos;
  }
}
