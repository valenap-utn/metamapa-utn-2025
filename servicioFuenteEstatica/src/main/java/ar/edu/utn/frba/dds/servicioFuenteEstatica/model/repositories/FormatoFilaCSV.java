package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import ar.edu.utn.frba.dds.utils.ReverseList;
import java.util.ArrayList;
import java.util.List;

public class FormatoFilaCSV {
  List<String> filaCSV;

  public FormatoFilaCSV() {
    this.filaCSV = new ArrayList<>();
  }

  public void agregarCampos(String ... campos){
    this.filaCSV.addAll(List.of(campos));
  }

  public String getTitulo() {
    return this.filaCSV.get(0);
  }

  public String getDescripcion() {
    return this.filaCSV.get(1);
  }

  public String getCategoria() {
    return this.filaCSV.get(2);
  }

  public String getLatitud() {
    return this.filaCSV.get(3);
  }

  public String getLongitud() {
    return this.filaCSV.get(4);
  }

  public String getFechaAcontecimiento() {
    List<String> fecha = List.of(this.filaCSV.get(5).split("/"));
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
