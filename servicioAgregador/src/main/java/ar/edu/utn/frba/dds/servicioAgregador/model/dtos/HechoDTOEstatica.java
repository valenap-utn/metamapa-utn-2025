package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HechoDTOEstatica  implements HechoDTO{
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private UsuarioDTO usuario;

  public Categoria getCategoria() {
    return new Categoria(this.categoria.getNombre());
  }
}
