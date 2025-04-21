package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

public class FiltroPorTitulo extends FiltroPorString{

  public FiltroPorTitulo(String cadenaAcomparar) {
    super(cadenaAcomparar);
  }

  @Override
  protected String obtenerUnTipoString(Hecho unHecho) {
    return unHecho.getTitulo();
  }
}
