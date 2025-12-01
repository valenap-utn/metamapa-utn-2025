package ar.edu.utn.frba.dds.model.dtos;

import java.util.List;
import lombok.Data;

@Data
public class EstadisticaDTO {
  private String nombre;
  private List<DatoEstadisticoDTO> datos;
  private Long totalDatos;
}
