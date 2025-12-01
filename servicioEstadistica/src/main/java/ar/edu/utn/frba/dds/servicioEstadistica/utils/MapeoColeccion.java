package ar.edu.utn.frba.dds.servicioEstadistica.utils;

import java.util.Map;
import lombok.Data;

@Data
public class MapeoColeccion {
  private String titulo;
  private Map<String, Long> hechosMapeados;

  public MapeoColeccion(String titulo, Map<String, Long> hechosMapeados) {
    this.titulo = titulo;
    this.hechosMapeados = hechosMapeados;
  }
}
