package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

public class FiltroNoEstaEliminado implements Filtro{
  @Override
  protected boolean hechoCumple(Hecho unHecho) {
    return !unHecho.isEliminado();
  }
}
