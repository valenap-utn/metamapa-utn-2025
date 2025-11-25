package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;


import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.List;
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
  @Id
  @Getter @Setter private Long id;

  @Column(name = "email")
  private String email;

  @Column(name = "nombre")
  @Getter @Setter private String nombre;

  @Column(name = "apellido")
  @Getter @Setter private String apellido;

  @Transient
  private Rol rol;

  @Transient
  private List<Permiso> permisos;


  public void cargarRolYPermisos(Rol rol, List<Permiso> permisos) {
    this.permisos = permisos;
    this.rol = rol;
  }

  public boolean tienePermisoDe(Permiso permiso, Rol rol) {
    return this.permisos.contains(permiso) && this.rol.equals(rol);
  }

  public UsuarioDTO getUsuarioDTO() {
    return UsuarioDTO.builder().id(this.id).nombre(this.nombre).apellido(this.apellido).email(this.email).build();
  }
}
