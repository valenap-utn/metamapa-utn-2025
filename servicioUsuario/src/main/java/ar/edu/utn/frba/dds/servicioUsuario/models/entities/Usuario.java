package ar.edu.utn.frba.dds.servicioUsuario.models.entities;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.UsuarioDTO;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, name = "nombre")
  private String nombre;
  @Column(name = "apellido")
  private String apellido;
  @Column(name = "fecha_nacimiento")
  private LocalDate fechaDeNacimiento;
  @Enumerated(EnumType.STRING)
  @Column(name = "rol")
  private Rol rol;
  @Enumerated(EnumType.STRING)
  @ElementCollection
  @CollectionTable(name = "permisos", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"))
  private List<Permiso> permisos = new ArrayList<>();
  @Column(name = "email")
  private String email;
  @Column(name = "password")
  private String password;
  @Column(name = "fecha_creacion", nullable = false)
  private LocalDate fechaCreacion;
  @Column(name = "provider_oauth")
  private String providerOAuth;

  public UsuarioDTO getUsuarioDTO() {
    return UsuarioDTO.builder().id(this.id).nombre(this.nombre).apellido(this.apellido)
        .rol(this.rol).permisos(this.permisos).email(this.email).build();
  }

  public void setearRol(Rol rol) {
    this.rol = rol;
    if (rol == Rol.ADMINISTRADOR){
      this.permisos.addAll( List.of( Permiso.CREARCOLECCION, Permiso.MODIFICARCOLECCION,
          Permiso.ELIMINARCOLECCION, Permiso.VISITARESTADISTICAS,
          Permiso.REVISARHECHO, Permiso.REVISARSOLICITUD, Permiso.SUBIDAARCHIVO));
    }
    if(rol == Rol.CONTRIBUYENTE){
      this.permisos.add(Permiso.MODIFICARHECHO);
    }
  }
}
