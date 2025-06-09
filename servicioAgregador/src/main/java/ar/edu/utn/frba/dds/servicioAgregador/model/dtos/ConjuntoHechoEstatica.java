package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import java.util.Set;
import lombok.Data;

@Data
public class ConjuntoHechoEstatica {
  Set<HechoDTOEstatica> hechos;
}
