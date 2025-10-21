package ar.edu.utn.frba.dds.model.entities;

import lombok.Data;

@Data
public class Ubicacion {
  private Float longitud;
  private Float latitud;
  private Direccion direccion;

  public String getProvincia() {
    return null;
  }
}
