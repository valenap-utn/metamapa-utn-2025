package ar.edu.utn.frba.dds.servicioAgregador.model.DTOs;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.ContenidoMultimedia;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import java.time.LocalDate;
import java.util.Set;
import lombok.Data;

@Data
public class HechoDTOCompleto implements HechoDTO<HechoDTOCompleto> {
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDate fechaAcontecimiento;
  private LocalDate fechaCarga;
  private Set<String> etiquetas;
  private boolean eliminado;
  private ContenidoMultimedia contenidoMultimedia;
}
