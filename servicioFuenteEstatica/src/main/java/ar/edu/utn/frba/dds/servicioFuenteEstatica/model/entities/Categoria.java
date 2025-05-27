package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Categoria {
  private String nombre;

  public Categoria(String nombre) {
    this.nombre = nombre;
  }

  public boolean esIgualA(Categoria categoria) {
    return this.nombre.equalsIgnoreCase(categoria.getNombre());
  }
}
