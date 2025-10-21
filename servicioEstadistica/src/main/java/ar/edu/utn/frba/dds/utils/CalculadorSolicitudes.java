package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.model.dtos.EstadisticaDTO;
import ar.edu.utn.frba.dds.model.entities.Solicitud;
import java.util.List;

public interface CalculadorSolicitudes {
  EstadisticaDTO calcular(List<Solicitud> solicitudes);
}
