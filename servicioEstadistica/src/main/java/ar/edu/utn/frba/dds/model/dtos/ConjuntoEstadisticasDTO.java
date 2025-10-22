package ar.edu.utn.frba.dds.model.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ConjuntoEstadisticasDTO {
  private List<EstadisticaDTO> estadisticas;

  public void agregarEstadistica(EstadisticaDTO estadistica) {
    this.estadisticas.add(estadistica);
  }
}
