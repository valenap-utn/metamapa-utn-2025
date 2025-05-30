package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.daos;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class HechoCSV { //para leer datos crudos del CSV
  @CsvBindByName(column = "Título") private String titulo;
  @CsvBindByName(column = "Descripción") private String descripcion;
  @CsvBindByName(column = "Categoría") private String categoria;
  @CsvBindByName(column = "Latitud") private float latitud;
  @CsvBindByName(column = "Longitud") private float longitud;
  @CsvBindByName(column = "Fecha del hecho") private String fecha;

}
