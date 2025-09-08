package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue("TITULO")
public class FiltroPorTitulo extends FiltroPorString{

  public FiltroPorTitulo(String cadenaAcomparar) {
    super(cadenaAcomparar);
  }

  @Override
  protected String obtenerUnTipoString(Hecho unHecho) {
    return unHecho.getTitulo();
  }

  @Override
  public CriterioDTO toCriterioDTO() {
    CriterioDTO criterioDTO = new CriterioDTO();
    criterioDTO.setTitulo(this.getCadenaAcomparar());
    criterioDTO.setTipo("TITULO");
    return criterioDTO;
  }
}
