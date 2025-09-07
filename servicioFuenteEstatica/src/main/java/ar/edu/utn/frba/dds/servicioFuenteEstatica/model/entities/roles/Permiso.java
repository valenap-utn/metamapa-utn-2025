package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "permiso")
public class Permiso {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String descripcion;

  public Permiso(String descripcion) {
    this.descripcion = descripcion;
  }
}
