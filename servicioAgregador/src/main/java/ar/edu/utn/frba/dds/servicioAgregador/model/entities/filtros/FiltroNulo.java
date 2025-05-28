package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public class FiltroNulo implements Filtro{

  @Override
  public boolean hechoCumple(Hecho _unHecho) {
    return true;
  }
}
