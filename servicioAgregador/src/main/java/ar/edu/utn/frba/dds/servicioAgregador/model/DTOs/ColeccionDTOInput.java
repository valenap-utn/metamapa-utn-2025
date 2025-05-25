package ar.edu.utn.frba.dds.servicioAgregador.model.DTOs;

import java.util.List;
import lombok.Data;

@Data
public class ColeccionDTOInput {
  private String nombre;
  private String descripcion;
  private Long usuario;
  private List<FuenteDTO> fuentes;
}
