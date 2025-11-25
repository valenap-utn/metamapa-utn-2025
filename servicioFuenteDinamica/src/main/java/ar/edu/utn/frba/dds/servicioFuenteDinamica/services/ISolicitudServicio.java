package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.SolicitudDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ISolicitudServicio {
  Solicitud crearSolicitud(SolicitudDTO solicitudDTO, MultipartFile contenidoMultimedia);
  Solicitud procesarSolicitud(Long id, RevisionDTO revisionDTO);

  List<Solicitud> findAllSolicitudes();
}
