package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public class FiltroNulo extends Filtro{

  @Override
  public boolean hechoCumple(Hecho _unHecho) {
    return true;
  }

  @Override
  public CriterioDTO toCriterioDTO() {
    return new CriterioDTO();
  }
}
