package ar.edu.utn.frba.dds.servicioAgregador.model.DTOs;

import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class FuenteHecho {
  private Long idFuente;
  private Set<HechoValueObject> hechosDTO;
}
