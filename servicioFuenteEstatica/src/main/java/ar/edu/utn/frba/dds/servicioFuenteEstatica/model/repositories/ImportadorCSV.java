package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import java.io.IOException;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ImportadorCSV {

  private final MultipartFile archivoCSV;

  public ImportadorCSV(MultipartFile archivoCSV) {
    this.archivoCSV = archivoCSV;
  }

  public Collection<HechoValueObject> importarHechosDataset(){
    try{
      LectorFilaCSV lector = new LectorFilaCSV();
      return lector.leerHechosDesde(archivoCSV);
    }catch(IOException e){
      throw new RuntimeException("Error al importar hechos desde el archivo CSV",e);
    }
  }
}
