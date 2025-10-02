package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTOModificacionDinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface IHechoServicio {
  Hecho crearHecho(HechoDTODinamica input, MultipartFile contenidoMultimedia);

  List<Hecho> obtenerHechosPublicos();

  Hecho modificarHecho(Long hechoId, HechoDTOModificacionDinamica nuevosDatos);

  Hecho revisarHecho(Long id, RevisionDTO revisionDTO);

  Hecho marcarComoEliminado(Long id);
}
