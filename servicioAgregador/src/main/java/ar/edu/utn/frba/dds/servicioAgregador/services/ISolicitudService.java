package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudInputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudOutputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;

public interface ISolicitudService {
  SolicitudOutputDTO crearSolicitud(SolicitudInputDTO solicitudInput);
  void aceptarSolicitud(Solicitud solicitud);
}
