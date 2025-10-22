package ar.edu.utn.frba.dds.model.entities;

import ar.edu.utn.frba.dds.model.dtos.FuenteDTO;
import lombok.Data;

@Data
public class Origen {
  private String url;
  private String tipo;

  public boolean equals(FuenteDTO fuente) {
    return this.url.equals(fuente.getUrl()) && this.tipo.equals(fuente.getTipoOrigen());
  }
}
