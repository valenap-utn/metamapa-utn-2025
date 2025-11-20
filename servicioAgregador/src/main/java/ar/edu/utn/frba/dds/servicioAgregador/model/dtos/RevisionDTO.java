package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import lombok.Data;

@Data
public class RevisionDTO {
  private String justificacion;
  private UsuarioDTO usuario;

  public Long getIdUsuario() {
    return usuario.getId();
  }
}
