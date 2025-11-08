package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles.Rol;
import java.util.List;
import lombok.Data;

@Data
public class UsuarioDTO {
  private Long id;
  private String email;
  private Rol rol;
  private List<Permiso> permisos;

  public boolean tienePermisoDe(Permiso permiso, Rol rol) {
    return this.permisos.contains(permiso) && this.rol.equals(rol);
  }
}
