package ar.edu.utn.frba.dds.servicioEstadistica.model.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ConjuntoSolicitudesAgregador {
  private List<SolicitudDTO> solicitudes;
}
