package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.ContenidoMultimedia;
import org.springframework.web.multipart.MultipartFile;

public interface IMultimediaRepository {
  ContenidoMultimedia saveFile(MultipartFile contenidoMultimedia);
}
