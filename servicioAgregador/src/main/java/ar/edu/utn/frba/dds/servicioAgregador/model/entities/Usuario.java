package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.Rol;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
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
@Getter
@Setter
public class Usuario {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false,name = "nombre")
  private String nombre;
  @Column(name = "apellido")
  private String apellido;
  @Column(name="fecha_nacimiento")
  private LocalDate fechaDeNacimiento;

  @JoinColumn(name = "rol_id", referencedColumnName = "id", nullable = false)
  @ManyToOne(cascade = CascadeType.PERSIST)
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
