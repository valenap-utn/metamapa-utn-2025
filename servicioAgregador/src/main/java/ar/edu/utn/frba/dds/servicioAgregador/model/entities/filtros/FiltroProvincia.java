package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;


@Entity
@DiscriminatorValue("UBICACIONPROVINCIA")
@NoArgsConstructor
public class FiltroProvincia extends FiltroUbicacion{

  public FiltroProvincia(String valor) {
    super(valor);
  }
  @Override
  protected String getValorHechoUbicacion(Hecho unHecho) {
    return unHecho.getProvincia();
  }

  @Override
  public CriterioDTO toCriterioDTO() {
    CriterioDTO criterioDTO = new CriterioDTO();
    criterioDTO.setProvincia(this.getValorUbicacion());
    criterioDTO.setTipo("UBICACIONPROVINCIA");
    return criterioDTO;
  }
}
