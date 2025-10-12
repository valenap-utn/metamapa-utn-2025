package ar.edu.utn.frba.dds.servicioUsuario.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Categoria {
  private String nombre;
  public Categoria(String nombre) {
    this.nombre = nombre;
  }
}
