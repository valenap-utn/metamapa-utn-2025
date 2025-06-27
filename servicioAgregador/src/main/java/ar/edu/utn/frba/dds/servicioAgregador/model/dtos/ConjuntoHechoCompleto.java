package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ConjuntoHechoCompleto {
  List<HechoDTOCompleto> hechos;
}
