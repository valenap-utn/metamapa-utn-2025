package ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="permiso")
public class Permiso {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;
  @Column(name = "descripcion")
  private String descripcion;

  public Permiso(String descripcion) {
    this.descripcion = descripcion;
  }

  public Permiso() {

  }
}
