package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import lombok.Data;

@Data
public class FuenteDTO {
  private Origen origen;
  private String tipo;
}
