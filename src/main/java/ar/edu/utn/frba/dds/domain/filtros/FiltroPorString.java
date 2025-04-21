package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import lombok.Getter;

@Getter
public abstract class FiltroPorString extends Filtro {
  private String cadenaAcomparar;

  public FiltroPorString(String cadenaAcomparar) {
    this.cadenaAcomparar = cadenaAcomparar;
  }
  @Override
  protected boolean hechoCumple(Hecho unHecho) {
    return this.cadenaAcomparar.equalsIgnoreCase(this.obtenerUnTipoString(unHecho));
  }

  protected abstract String obtenerUnTipoString(Hecho unHecho);
}
