package ar.edu.utn.frba.dds.model.dtos;

import ar.edu.utn.frba.dds.model.entities.Categoria;
import ar.edu.utn.frba.dds.model.entities.ContenidoMultimedia;
import ar.edu.utn.frba.dds.model.entities.Origen;
import ar.edu.utn.frba.dds.model.entities.Ubicacion;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class HechoDTO {
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private Set<String> etiquetas;
  private boolean eliminado;
  private ContenidoMultimedia contenidoMultimedia;
  private Long idUsuario;
  private Origen origen;
}
