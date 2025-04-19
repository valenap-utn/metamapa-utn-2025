package ar.edu.utn.frba.dds.domain.entities;

import ar.edu.utn.frba.dds.domain.entities.roles.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
  private String nombre;
  private String apellido;
  private Integer edad;
  private Rol rol;

  public Usuario of(Rol rol){
    return Usuario
        .builder()
        .rol(rol)
        .nombre(null)
        .apellido(null)
        .edad(null)
        .build();
  }

  public Usuario of(Rol rol, String nombre){
    return Usuario
        .builder()
        .rol(rol)
        .nombre(nombre)
        .apellido(null)
        .edad(null)
        .build();
  }

  public Usuario of(Rol rol, String nombre, String apellido){
    return Usuario
        .builder()
        .rol(rol)
        .nombre(nombre)
        .apellido(apellido)
        .edad(null)
        .build();
  }

  public Usuario of(Rol rol, String nombre, String apellido, Integer edad){
    return Usuario
        .builder()
        .rol(rol)
        .nombre(nombre)
        .apellido(apellido)
        .edad(edad)
        .build();
  }

  public Rol getRol() {
    return this.rol;
  }


}
