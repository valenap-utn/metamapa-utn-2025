package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;

@Getter
@Inheritance( strategy = InheritanceType.SINGLE_TABLE)

public abstract class FiltroPorString extends Filtro {
  private final String cadenaAcomparar;

  public FiltroPorString(String cadenaAcomparar) {
    this.cadenaAcomparar = cadenaAcomparar;
  }
  @Override
  public boolean hechoCumple(Hecho unHecho) {
    return this.cadenaAcomparar.equalsIgnoreCase(this.obtenerUnTipoString(unHecho));
  }

  protected abstract String obtenerUnTipoString(Hecho unHecho);

}
