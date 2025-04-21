package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Ubicacion;
import java.time.LocalDate;

public class FiltroUbicacion  extends Filtro{
  private Ubicacion ubicacion;
  public FiltroUbicacion(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
  }

  @Override
  protected boolean hechoCumple(Hecho unHecho){
      return ubicacion.equals(unHecho.getUbicacion());
  }

}
