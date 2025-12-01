package ar.edu.utn.frba.dds.servicioEstadistica.utils;

import ar.edu.utn.frba.dds.servicioEstadistica.model.dtos.EstadisticaDTO;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.Solicitud;
import java.util.List;

public interface CalculadorSolicitudes {
  EstadisticaDTO calcular(List<Solicitud> solicitudes);
}
