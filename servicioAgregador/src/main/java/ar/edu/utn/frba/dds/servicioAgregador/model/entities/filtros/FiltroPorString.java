package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Inheritance( strategy = InheritanceType.SINGLE_TABLE)
@Entity
@NoArgsConstructor
public abstract class FiltroPorString extends Filtro {
  @Column(name = "cadena_a_comparar")
  private String cadenaAcomparar;

  public FiltroPorString(String cadenaAcomparar) {
    this.cadenaAcomparar = cadenaAcomparar;
  }
  @Override
  public boolean hechoCumple(Hecho unHecho) {
    return this.cadenaAcomparar.equalsIgnoreCase(this.obtenerUnTipoString(unHecho));
  }

  protected abstract String obtenerUnTipoString(Hecho unHecho);

}
