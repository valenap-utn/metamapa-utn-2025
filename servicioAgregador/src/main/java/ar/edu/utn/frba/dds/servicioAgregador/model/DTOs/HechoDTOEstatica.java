package ar.edu.utn.frba.dds.servicioAgregador.model.DTOs;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import java.time.LocalDate;
import lombok.Data;

@Data
public class HechoDTOEstatica  implements HechoDTO<HechoDTOEstatica>{
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDate fechaAcontecimiento;
  private LocalDate fechaCarga;
}
