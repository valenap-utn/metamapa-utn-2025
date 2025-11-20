package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.ContenidoMultimedia;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Ubicacion;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HechoDTODinamica  {
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private ContenidoMultimedia contenidoMultimedia;
  private String contenido;
  private UsuarioDTO usuario;

  public Long getIdUsuario() {
    return usuario.getId();
  }
}
