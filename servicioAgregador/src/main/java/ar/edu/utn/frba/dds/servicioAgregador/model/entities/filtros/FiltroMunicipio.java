package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("UBICACIONMUNICIPIO")
@NoArgsConstructor
public class FiltroMunicipio extends FiltroUbicacion{
  public FiltroMunicipio(String valor) {
    super(valor);
  }

  @Override
  protected String getValorHechoUbicacion(Hecho unHecho) {
    return unHecho.getMunicipio();
  }

  @Override
  public CriterioDTO toCriterioDTO() {
    CriterioDTO criterioDTO = new CriterioDTO();
    criterioDTO.setMunicipio(this.getValorUbicacion());
    criterioDTO.setTipo("UBICACIONMUNICIPIO");
    return criterioDTO;
  }
}
