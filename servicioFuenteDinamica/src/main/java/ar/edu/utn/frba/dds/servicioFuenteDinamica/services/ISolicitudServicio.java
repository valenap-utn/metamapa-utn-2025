package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface ISolicitudServicio {
  Solicitud crearSolicitud(Long hechoId, Usuario usuario, String contenido);
  Solicitud procesarSolicitud(Long id, String estadoStr, String justificacion);
}
