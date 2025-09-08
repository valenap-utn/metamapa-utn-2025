package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue("UBICACION")
public class FiltroUbicacion  extends Filtro{
  private Ubicacion ubicacion;
  public FiltroUbicacion(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
  }

  @Override
  public boolean hechoCumple(Hecho unHecho){
      return ubicacion.equals(unHecho.getUbicacion());
  }

  @Override
  public CriterioDTO toCriterioDTO() {
    CriterioDTO criterioDTO = new CriterioDTO();
    criterioDTO.setUbicacion(this.ubicacion);
    criterioDTO.setTipo("UBICACION");
    return criterioDTO;
  }

}
