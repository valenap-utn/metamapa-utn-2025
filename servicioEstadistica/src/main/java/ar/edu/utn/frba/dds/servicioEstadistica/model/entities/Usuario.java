package ar.edu.utn.frba.dds.servicioEstadistica.model.entities;


import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.roles.Rol;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
  @Getter @Setter private Long id;
  private String email;

  @Getter @Setter private String nombre;

  @Getter @Setter private String apellido;

  private Rol rol;

  private List<Permiso> permisos;


  public boolean tienePermisoDe(Permiso permiso, Rol rol) {
    return this.permisos.contains(permiso) && this.rol.equals(rol);
  }

}
