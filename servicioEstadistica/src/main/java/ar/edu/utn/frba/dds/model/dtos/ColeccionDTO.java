package ar.edu.utn.frba.dds.model.dtos;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class ColeccionDTO {
  private UUID id;
  private String titulo;
  private String descripcion;
  private List<FuenteDTO> fuentes;
}
