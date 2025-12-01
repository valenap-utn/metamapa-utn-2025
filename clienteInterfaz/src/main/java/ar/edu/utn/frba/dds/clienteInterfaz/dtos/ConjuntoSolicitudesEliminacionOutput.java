package ar.edu.utn.frba.dds.clienteInterfaz.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ConjuntoSolicitudesEliminacionOutput {
  private List<SolicitudEliminacionDTO> solicitudes;
}
