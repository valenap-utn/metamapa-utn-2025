package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Ubicacion;
import com.opencsv.CSVReader;

import lombok.SneakyThrows; //
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LectorFilaCSV {

  private HechoValueObject convertirAFilaHecho(String[] filaCSV){
    return new HechoValueObject(
        filaCSV[0],
        filaCSV[1],
        new Categoria(filaCSV[2]),
        new Ubicacion(
            Float.parseFloat(filaCSV[4]), //longitud
            Float.parseFloat(filaCSV[3]) //latitud
        ),
        parsearFecha(filaCSV[5])
    );
  }

  private LocalDate parsearFecha(String fechaDDMMYYYY) {
    String[] partes = fechaDDMMYYYY.split("/");
    return LocalDate.of(
        Integer.parseInt(partes[0]),  // Día
        Integer.parseInt(partes[1]), // Mes
        Integer.parseInt(partes[2]) // Año
    );
  }

  @SneakyThrows
  public Collection<HechoValueObject> leerHechosDesde(MultipartFile archivoCSV) throws IOException {
    Map<String, HechoValueObject> hechosPorTitulo = new HashMap<>();
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(archivoCSV.getInputStream()))){
      String[] fila;
      boolean primera = true;
      while((fila = csvReader.readNext()) != null){ //usé lombok SneakyThrows para que funcione (risky)
        if(primera){
          primera = false;
          continue;
        }
        HechoValueObject hecho = this.convertirAFilaHecho(fila);
        hechosPorTitulo.put(hecho.getTitulo(), hecho); //pisa si el título ya estaba
      }
    }
    return hechosPorTitulo.values();
  }

}
