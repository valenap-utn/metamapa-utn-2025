package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

public class FiltroPorDescripcion extends FiltroPorString{

  public FiltroPorDescripcion(String cadenaAcomparar) {
    super(cadenaAcomparar);
  }

  @Override
  protected String obtenerUnTipoString(Hecho unHecho) {
    return unHecho.getDescripcion();
  }
}
