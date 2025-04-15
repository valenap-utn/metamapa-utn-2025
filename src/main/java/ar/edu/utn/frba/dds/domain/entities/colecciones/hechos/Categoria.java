package ar.edu.utn.frba.dds.domain.entities.colecciones.hechos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Categoria {
  private String nombre;

  public boolean esIgualA(Categoria categoria) {
    return this.nombre.equalsIgnoreCase(categoria.getNombre());
  }
}
