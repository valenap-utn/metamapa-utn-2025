package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="direccion")
@Data
public class Direccion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "departamento")
  private String departamento = "";

  @Column(name = "municipio")
  private String municipio = "";

  @Column(name = "provincia")
  private String provincia = "";

  @Column(name = "normalizada", nullable = false)
  private Boolean normalizada = Boolean.FALSE;

  public void marcarNormalizada() {
    this.normalizada = true;
  }

  public boolean estaNormalizada() {
    return this.normalizada;
  }
}
