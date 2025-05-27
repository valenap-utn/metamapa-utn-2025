package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Ubicacion;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
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

//  public List<HechoValueObject> importarHechosDataset(){
//    List<FormatoFilaCSV> contenidoFilasCSV = obtenerContenidoDeFilasCSV();
//    return transformarfilasCSVAHechosValueObject(contenidoFilasCSV);
//  }
//
//  private List<FormatoFilaCSV> obtenerContenidoDeFilasCSV(){
//    try(Reader reader = new InputStreamReader(archivoCSV.getInputStream())){
//      LectorFilaCSV lector = new LectorFilaCSV();
//      return lector.obtenerFilasCSV(reader);
//    }catch(IOException | CsvException e){
//      throw new RuntimeException("Error leyendo el archivo CSV", e);
//    }
//  }
//
//  private List<HechoValueObject> transformarfilasCSVAHechosValueObject(List<FormatoFilaCSV> filasCSV){
//    return filasCSV.stream().map(fila -> new HechoValueObject(
//        fila.getTitulo(),
//        fila.getDescripcion(),
//        new Categoria(fila.getCategoria()),
//        new Ubicacion(
//            Float.parseFloat(fila.getLongitud()),
//            Float.parseFloat(fila.getLatitud())
//        ),
//        LocalDate.parse(fila.getFechaAcontecimiento())
//    )).toList();
//  }

}
