package ar.edu.utn.frba.dds.servicioAgregador.model.DTOs;

import java.util.Set;
import lombok.Data;

public interface ConjuntoHechoDTO<T> {
  public <R> Set<HechoDTO<R>> getHechos();
}
