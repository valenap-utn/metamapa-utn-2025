package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoSolicitudesOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudInputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudOutputDTO;

public interface ISolicitudService {
  SolicitudOutputDTO crearSolicitud(SolicitudInputDTO solicitudInput);
  SolicitudOutputDTO aceptarSolicitud(Long solicitud);

  SolicitudOutputDTO eliminarSolicitud(Long idSolicitud);

  ConjuntoSolicitudesOutput buscarSolicitudes();
}
