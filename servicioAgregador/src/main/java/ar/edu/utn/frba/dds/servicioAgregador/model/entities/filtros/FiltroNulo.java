package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

public class FiltroNulo implements Filtro{

  @Override
  protected boolean hechoCumple(Hecho _unHecho) {
    return true;
  }
}
