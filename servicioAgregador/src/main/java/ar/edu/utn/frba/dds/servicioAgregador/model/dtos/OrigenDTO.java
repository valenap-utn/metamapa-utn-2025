package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrigenDTO {
  private String tipo;
  private String url;
}
