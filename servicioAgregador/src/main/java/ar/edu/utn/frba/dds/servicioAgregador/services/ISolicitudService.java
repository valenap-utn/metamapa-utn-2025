package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudInputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;

public interface ISolicitudService {
  Solicitud crearSolicitud(SolicitudInputDTO solicitudInput);
  void aceptarSolicitud(Solicitud solicitud);
}
