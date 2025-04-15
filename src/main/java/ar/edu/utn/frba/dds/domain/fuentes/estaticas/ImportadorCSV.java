package ar.edu.utn.frba.dds.domain.fuentes.estaticas;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.HechoValueObject;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.ColeccionHechoValueObject;

import java.util.List;

public class ImportadorCSV {
  private String pathCsv;

  public ImportadorCSV(String pathCsv) {
    this.pathCsv = pathCsv;
  }

  public ColeccionHechoValueObject importarHechosDataset() {
    URL fileUrl = ImportadorCSV.class.getClassLoader().getResource("data.csv");

    CSVReader reader = new CSVReader(new FileReader(fileUrl.getFile()));

    List<String[]> filasCSV = reader.readAll();

    return transformarfilasCSVAHechosValueObject(filasCSV);
  }

  public ColeccionHechoValueObject transformarfilasCSVAHechosValueObject(List<String[]> filasCSV) {
    ColeccionHechoValueObject coleccionHechos = new ColeccionHechoValueObject();
    //TODO: Logica de parsear los datos de String a los diversos datos
    for(String[] filaCSV : filasCSV) {
      coleccionHechos.agregarHechosDataset(new HechoValueObject(
          filasCSV.get(0),
          filasCSV.get(1),
          filasCSV.get(2),
          filasCSV.get(3)
      ));
    }
  }

}
