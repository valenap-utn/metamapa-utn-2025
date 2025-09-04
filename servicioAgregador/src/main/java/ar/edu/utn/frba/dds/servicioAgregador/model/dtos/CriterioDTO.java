package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CriterioDTO {
  private String tipo;
  private LocalDateTime fechaCargaInicial;
  private LocalDateTime fechaCargaFinal;
  private LocalDateTime fechaAcontecimientoInicial;
  private LocalDateTime fechaAcontecimientoFinal;
  private String titulo;
  private String descripcion;
  private String categoria;
  private Ubicacion ubicacion;
}
