package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output;

import java.util.Set;
import lombok.Data;

@Data
public class ConjuntoHechoProxy {
  Set<HechoOutputDTO> hechos;
}
