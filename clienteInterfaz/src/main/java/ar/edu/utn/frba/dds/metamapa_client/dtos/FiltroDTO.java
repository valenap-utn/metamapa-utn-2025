package ar.edu.utn.frba.dds.metamapa_client.dtos;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class FiltroDTO {
  private String categoria;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime fecha_reporte_desde;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime fecha_reporte_hasta;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime fecha_acontecimiento_desde;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime fecha_acontecimiento_hasta;
  private Float latitud;
  private Float longitud;
  private Boolean curada = false;
  private Boolean entiemporeal = false;

  private String provincia;
  private String municipio;
  private String departamento;

}
