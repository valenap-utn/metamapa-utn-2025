package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ConjuntoSolicitud {
  private List<SolicitudDTO> solicitudes;
}
