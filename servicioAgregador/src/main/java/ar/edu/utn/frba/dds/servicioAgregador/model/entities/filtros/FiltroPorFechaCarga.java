package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("FECHACARGA")
public class FiltroPorFechaCarga extends FiltroPorFecha {
    public FiltroPorFechaCarga(LocalDateTime desde, LocalDateTime hasta) {
        super(desde, hasta);
    }

  @Override
    protected LocalDateTime obtenerUnTipoFecha(Hecho unHecho){
        return unHecho.getFechaCarga();
    }

  @Override
  public CriterioDTO toCriterioDTO() {
    CriterioDTO criterioDTO = new CriterioDTO();
    criterioDTO.setFechaCargaInicial(this.getDesde());
    criterioDTO.setFechaCargaFinal(this.getHasta());
    criterioDTO.setTipo("FECHACARGA");
    return criterioDTO;
  }
}
