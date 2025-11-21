package ar.edu.utn.frba.dds.servicioUsuario.models.dtos;

import lombok.Data;

@Data
public class RevisionDTO {
  private String estado;
  private String comentario;
  private UsuarioDTO usuario;
}
