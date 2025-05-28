package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;

public class FiltroUbicacion  implements Filtro{
  private Ubicacion ubicacion;
  public FiltroUbicacion(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
  }

  @Override
  public boolean hechoCumple(Hecho unHecho){
      return ubicacion.equals(unHecho.getUbicacion());
  }

}
