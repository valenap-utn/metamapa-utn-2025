package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities;


import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles.Rol;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter @Setter private Long id;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Column(name = "fecha_nacimiento")
  private LocalDate fechaDeNacimiento;

  @JoinColumn(name = "rol_id", referencedColumnName = "id", nullable = false)
  @ManyToOne
  private Rol rol;

  public static Usuario of(Long id){
    return Usuario.builder().id(id).build();
  }

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
