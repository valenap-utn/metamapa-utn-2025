package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.EstadoSolicitud;

public class RevisarEstado {
  public revisar() {
    EstadoSolicitud estado = EstadoSolicitud.valueOf(estadoStr);
    solicitud.setEstado(estado);
    solicitud.setJustificacion(justificacion);
  }
}
