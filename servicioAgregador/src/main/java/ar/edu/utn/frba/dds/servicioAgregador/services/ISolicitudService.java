package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;

public interface ISolicitudService {
  Solicitud crearSolicitud(Hecho hecho, Usuario user, String texto);
  void aceptarSolicitud(Solicitud solicitud);
}
