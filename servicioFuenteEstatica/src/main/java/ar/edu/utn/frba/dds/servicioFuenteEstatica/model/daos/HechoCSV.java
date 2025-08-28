package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.daos;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.utils.ReverseList;
import com.opencsv.bean.CsvBindByName;
import java.util.List;
import lombok.Getter;

@Getter
public class HechoCSV { //para leer datos crudos del CSV
  @CsvBindByName(column = "Título") private String titulo;
  @CsvBindByName(column = "Descripción") private String descripcion;
  @CsvBindByName(column = "Categoría") private String categoria;
  @CsvBindByName(column = "Latitud") private float latitud;
  @CsvBindByName(column = "Longitud") private float longitud;
  @CsvBindByName(column = "Fecha del hecho") private String fecha;

  public String getFechaAcontecimiento() {
    List<String> fecha = List.of(this.fecha.split("/"));
    List<String> fechaAlReves = ReverseList.reverse(fecha);
    return fechaAlReves.stream().reduce("",
            this::formatoISOFecha);
  }

  private String formatoISOFecha(String unaCadena, String otraCadena) {
    String separador = "-";
    if (unaCadena.isEmpty())
      separador = "";
    return unaCadena + separador + otraCadena;
  }

}
