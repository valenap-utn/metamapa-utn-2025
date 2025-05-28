package ar.edu.utn.frba.dds.servicioAgregador.model.DTOs;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.ContenidoMultimedia;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import java.time.LocalDate;
import lombok.Data;

@Data
public class HechoDTODinamica implements HechoDTO<HechoDTODinamica> {
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDate fechaAcontecimiento;
  private LocalDate fechaCarga;
  private boolean eliminado;
  private ContenidoMultimedia contenidoMultimedia;
}
