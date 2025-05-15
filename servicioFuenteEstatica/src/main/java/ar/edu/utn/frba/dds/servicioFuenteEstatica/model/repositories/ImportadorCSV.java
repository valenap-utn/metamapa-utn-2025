package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Categoria;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.HechoValueObject;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.ColeccionHechoValueObject;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Ubicacion;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImportadorCSV {

  private String pathCsv;

  public ImportadorCSV(String pathCsv) {
    this.pathCsv = pathCsv;
  }

  public ColeccionHechoValueObject importarHechosDataset() {
    List<FormatoFilaCSV> contenidoFilasCSV = obtenerContenidoDeFilasCSV();
    return transformarfilasCSVAHechosValueObject(contenidoFilasCSV);
  }

  private List<FormatoFilaCSV> obtenerContenidoDeFilasCSV()  {
    List<FormatoFilaCSV> filasCSV;
    LectorFilaCSV lector = new LectorFilaCSV();
    try {
      filasCSV = lector.obtenerFilasCSV(this.getPathCsv());
    } catch (IOException excepcionIO) {
      throw new RuntimeException(excepcionIO);
    } catch (CsvException excepcionCSV) {
      throw new RuntimeException(excepcionCSV);
    }
    return filasCSV;
  }


  public ColeccionHechoValueObject transformarfilasCSVAHechosValueObject(List<FormatoFilaCSV> filasCSV) {
    ColeccionHechoValueObject coleccionHechos = new ColeccionHechoValueObject();

    for(FormatoFilaCSV filaCSV : filasCSV) {
      coleccionHechos.agregarHechosDataset(new HechoValueObject(
              filaCSV.getTitulo(), //Titulo
              filaCSV.getDescripcion(),
              new Categoria(filaCSV.getCategoria()),
              new Ubicacion(
                      Float.parseFloat(filaCSV.getLongitud()),
                      Float.parseFloat(filaCSV.getLatitud())),
              LocalDate.parse(filaCSV.getFechaAcontecimiento())
               ));
    }
    return coleccionHechos;
  }

}
