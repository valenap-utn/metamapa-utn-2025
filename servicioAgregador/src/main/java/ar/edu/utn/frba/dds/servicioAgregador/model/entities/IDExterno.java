package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import lombok.Data;

@Data
public class IDExterno {
  private Long idExterno;
  private Long idOrigen;

  public IDExterno(Hecho hecho) {
    this.idExterno = hecho.getIdExterno();
    this.idOrigen = hecho.getIdOrigen();
  }
  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof IDExterno)) return false;
    IDExterno other = (IDExterno) o;
    return this.idExterno.equals(other.idExterno) && this.idOrigen.equals(other.idOrigen);
  }
}
