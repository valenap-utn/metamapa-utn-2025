package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ContenidoMultimedia {
  @Column(name = "contenidoMultimedia_nombre")
  private String nombre;
  @Column(name="contenidoMultimedia_path")
  private String path;
}
