package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.ContenidoMultimedia;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

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
    return new ContenidoMultimedia(contenidoMultimedia.getOriginalFilename(), direccionFinal.toString());
  }
}
