package ar.edu.utn.frba.dds.servicioAgregador.model.DTOs;

import java.util.Set;
import lombok.Data;

public interface ConjuntoHechoDTO {
  public Set<HechoDTO> getHechos();
}
