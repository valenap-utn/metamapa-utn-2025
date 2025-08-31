package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import lombok.Data;

@Data
public class ContenidoMultimedia {
  private String nombre;
  private String path;

  public ContenidoMultimedia(String nombre, String path) {
    this.nombre = nombre;
    this.path = path;
  }
}
