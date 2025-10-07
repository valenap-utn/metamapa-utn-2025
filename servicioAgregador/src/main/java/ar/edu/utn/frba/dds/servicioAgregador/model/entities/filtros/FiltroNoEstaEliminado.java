package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NOESTAELIMINADO")
public class FiltroNoEstaEliminado extends Filtro{
  @Override
  public boolean hechoCumple(Hecho unHecho) {
    return !unHecho.isEliminado();
  }

  @Override
  public CriterioDTO toCriterioDTO() {
    return new CriterioDTO();
  }
}
