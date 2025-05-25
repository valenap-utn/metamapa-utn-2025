package ar.edu.utn.frba.dds.servicioAgregador.model.DTOs;

import java.util.List;
import lombok.Data;

@Data
public class ColeccionDTOOutput {
  private String id;
  private String nombre;
  private String descripcion;
  private List<FuenteDTO> fuentes;
}
