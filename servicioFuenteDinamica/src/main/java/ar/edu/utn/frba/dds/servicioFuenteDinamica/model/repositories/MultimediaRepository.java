package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.controllers.HechoSolicitudController;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.ContenidoMultimedia;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Repository
public class MultimediaRepository implements IMultimediaRepository {
  private final Path path;

  public MultimediaRepository() {
    this.path = Paths.get("Multimedia");

    try {
      Files.createDirectories(this.path);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }
  @Override
  public ContenidoMultimedia saveFile(MultipartFile contenidoMultimedia ) {
    Path direccionFinal;
    direccionFinal = path.resolve(Objects.requireNonNull(contenidoMultimedia.getOriginalFilename()));
    try {
      Files.copy(contenidoMultimedia.getInputStream(), direccionFinal);
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        System.out.println("El archivo ya existe");
       // throw new ArchivoYaExiste("El archivo ya existe.");
      } else {
        throw new RuntimeException(e.getMessage());
      }
    }
    System.out.println(contenidoMultimedia.getContentType());
    String url = MvcUriComponentsBuilder
            .fromMethodName(HechoSolicitudController.class, "getFile", contenidoMultimedia.getOriginalFilename()).build().toUriString();
    return new ContenidoMultimedia(contenidoMultimedia.getOriginalFilename(), url, contenidoMultimedia.getContentType() == null ? Boolean.FALSE : !contenidoMultimedia.getContentType().startsWith("image"));
  }
  @Override
  public Resource cargarMultimedia(String filename) {
    try {
      Path file = path.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }
}
