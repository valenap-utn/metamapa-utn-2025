package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("FECHAACONTECIMIENTO")
public class FiltroPorFechaAcontecimiento extends FiltroPorFecha {
    public FiltroPorFechaAcontecimiento(LocalDateTime desde, LocalDateTime hasta) {
        super(desde, hasta);
    }

    @Override
    protected LocalDateTime obtenerUnTipoFecha(Hecho hecho) {
        return hecho.getFechaAcontecimiento();
    }

    @Override
    public CriterioDTO toCriterioDTO() {
        CriterioDTO criterioDTO = new CriterioDTO();
        criterioDTO.setFechaAcontecimientoInicial(this.getDesde());
        criterioDTO.setFechaAcontecimientoFinal(this.getHasta());
        criterioDTO.setTipo("FECHAACONTECIMIENTO");
        return criterioDTO;
    }
}
