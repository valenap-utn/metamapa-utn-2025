package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public class FiltroNoEstaEliminado implements Filtro{
  @Override
  public boolean hechoCumple(Hecho unHecho) {
    return !unHecho.isEliminado();
  }
}
