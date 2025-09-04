package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public class FiltroPorDescripcion extends FiltroPorString{

  public FiltroPorDescripcion(String cadenaAcomparar) {
    super(cadenaAcomparar);
  }

  @Override
  protected String obtenerUnTipoString(Hecho unHecho) {
    return unHecho.getDescripcion();
  }

  @Override
  public CriterioDTO toCriterioDTO() {
    CriterioDTO criterioDTO = new CriterioDTO();
    criterioDTO.setDescripcion(this.getCadenaAcomparar());
    criterioDTO.setTipo("DESCRIPCION");
    return criterioDTO;
  }
}
