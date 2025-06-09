package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.Rol;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
  private String nombre;
  private String apellido;
  private LocalDate fechaDeNacimiento;
  private Rol rol;

  public static Usuario of(Rol rol){
    return Usuario
        .builder()
        .rol(rol)
        .nombre(null)
        .apellido(null)
        .fechaDeNacimiento(null)
        .build();
  }

  public static Usuario of(Rol rol, String nombre){
    return Usuario
        .builder()
        .rol(rol)
        .nombre(nombre)
        .apellido(null)
        .fechaDeNacimiento(null)
        .build();
  }

  public static Usuario of(Rol rol, String nombre, String apellido){
    return Usuario
        .builder()
        .rol(rol)
        .nombre(nombre)
        .apellido(apellido)
        .fechaDeNacimiento(null)
        .build();
  }

  public static Usuario of(Rol rol, String nombre, String apellido, LocalDate fechaDeNacimiento){
    return Usuario
        .builder()
        .rol(rol)
        .nombre(nombre)
        .apellido(apellido)
        .fechaDeNacimiento(fechaDeNacimiento)
        .build();
  }

  public Integer getEdad() {
    return LocalDate.now().getYear() - this.fechaDeNacimiento.getYear();
  }


  public boolean tienePermisoDe(Permiso permiso) {
    return this.rol.tienePermisoDe(permiso);
  }
}
