package ar.edu.utn.frba.dds.servicioUsuario.models.dtos;

import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Permiso;
import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Rol;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {
  private Long id;
  private String email;
  private String nombre;
  private String apellido;
  private Rol rol;
  private List<Permiso> permisos;

}
