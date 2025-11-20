package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.SolicitudDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import org.springframework.stereotype.Service;

@Service
public interface ISolicitudServicio {
  Solicitud crearSolicitud(SolicitudDTO solicitudDTO);
  Solicitud procesarSolicitud(Long id, RevisionDTO revisionDTO);
}
