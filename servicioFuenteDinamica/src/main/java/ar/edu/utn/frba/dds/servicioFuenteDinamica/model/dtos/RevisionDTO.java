package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos;

import lombok.Data;

@Data
public class RevisionDTO {
  private String estado;
  private String comentario;
  private UsuarioDTO usuario;

  public Long getIdUsuario() {
    return this.usuario.getId();
  }
}
