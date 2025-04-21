package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

public class FiltroNoEstaEliminado extends Filtro{
  @Override
  protected boolean hechoCumple(Hecho unHecho) {
    return !unHecho.isEliminado();
  }
}
