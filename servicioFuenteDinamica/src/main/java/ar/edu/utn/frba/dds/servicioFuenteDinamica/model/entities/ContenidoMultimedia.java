package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class ContenidoMultimedia {
  @Column(name = "contenidoMultimedia_nombre", nullable = false)
  private String nombre;

  @Column(name="contenidoMultimedia_path", nullable = false)
  private String path;

//  public ContenidoMultimedia(String nombre, String path) {
//    this.nombre = nombre;
//    this.path = path;
//  }
}
