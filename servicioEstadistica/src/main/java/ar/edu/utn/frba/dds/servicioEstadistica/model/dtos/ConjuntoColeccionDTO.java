package ar.edu.utn.frba.dds.servicioEstadistica.model.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ConjuntoColeccionDTO {
  private List<ColeccionDTO> colecciones;
}
