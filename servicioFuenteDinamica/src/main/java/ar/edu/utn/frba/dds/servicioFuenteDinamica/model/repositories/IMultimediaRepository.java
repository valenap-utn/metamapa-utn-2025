package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.ContenidoMultimedia;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IMultimediaRepository {
  ContenidoMultimedia saveFile(MultipartFile contenidoMultimedia);

  Resource cargarMultimedia(String filename);
}
