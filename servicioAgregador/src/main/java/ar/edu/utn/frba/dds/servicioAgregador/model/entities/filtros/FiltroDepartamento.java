package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;


@Entity
@DiscriminatorValue("UBICACIONDEPARTAMENTO")
@NoArgsConstructor
public class FiltroDepartamento extends FiltroUbicacion{

  public FiltroDepartamento(String valor, Double cantidadAceptable) {
    super(valor, cantidadAceptable);
  }

  @Override
  protected String getValorHechoUbicacion(Hecho unHecho) {
    return unHecho.getDepartamento();
  }

  @Override
  public CriterioDTO toCriterioDTO() {
    CriterioDTO criterioDTO = new CriterioDTO();
    criterioDTO.setDepartamento(this.getValorUbicacion());
    criterioDTO.setTipo("UBICACIONDEPARTAMENTO");
    return criterioDTO;
  }
}
