package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolicitudInputDTO {
  @NotNull
  private Long idHecho;

  private UsuarioDTO usuario;

  @NotBlank
  private String justificacion;

  public Long getUsuarioId() {
    return usuario == null ? null:usuario.getId();
  }
}
