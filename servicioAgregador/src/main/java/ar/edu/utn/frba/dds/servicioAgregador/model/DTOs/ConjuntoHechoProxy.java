package ar.edu.utn.frba.dds.servicioAgregador.model.DTOs;

import java.util.Set;
import lombok.Data;

@Data
public class ConjuntoHechoProxy{
  Set<HechoDTOProxy> hechos;
}
