package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import lombok.Getter;

@Getter
public abstract class FiltroPorString implements Filtro {
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
