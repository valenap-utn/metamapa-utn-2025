package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorFilaCSV {
  public List<FormatoFilaCSV> obtenerFilasCSV(String rutaArchivo) throws IOException, CsvException {
    List<FormatoFilaCSV> filasCSVHechoValueObject = new ArrayList<>();
    FileReader filereader = new FileReader(rutaArchivo);
    CSVReader lectorCSV = this.configurarCSVReader(filereader);
    List<String[]> filasCSV = lectorCSV.readAll();
    return filasCSV.stream().map(filaCSV -> this.convertirAFilaCSV(filaCSV)).toList();
  }

  private FormatoFilaCSV convertirAFilaCSV(String[] filaCSV) {
    FormatoFilaCSV filaNueva = new FormatoFilaCSV();
    filaNueva.agregarCampos(filaCSV);
    return filaNueva;
  }

  private CSVReader configurarCSVReader(FileReader fileReader) {
    CSVParser formatoCSV = new CSVParserBuilder()
            .withSeparator(',')
            .withQuoteChar('\"')
            .build();

    return new CSVReaderBuilder(fileReader)
            .withCSVParser(formatoCSV)
            .withSkipLines(1)
            .build();
  }
}
