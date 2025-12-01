package ar.edu.utn.frba.dds.servicioEstadistica.model.dtos;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ConjuntoEstadisticasDTO {
  private List<EstadisticaDTO> estadisticas = new ArrayList<>();

  public void agregarEstadistica(EstadisticaDTO estadistica) {
    this.estadisticas.add(estadistica);
  }
}
