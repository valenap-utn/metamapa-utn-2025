package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="categoria")
@NoArgsConstructor
public class Categoria {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter
  @Getter
  private Long id;

  @Column(name = "nombre", nullable = false)
  @Setter
  @Getter
  private String nombre;

  @Column(name = "normalizada", nullable = false)
  @Setter
  @Getter
  private Boolean normalizada = Boolean.FALSE;

  public Categoria(String nombre) {
    this.nombre = nombre;
  }

  public boolean esIgualA(Categoria categoria) {
    return this.nombre.equalsIgnoreCase(categoria.getNombre());
  }

  public void marcarNormalizada() {
    this.normalizada = true;
  }
}
