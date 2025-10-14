package ar.edu.utn.frba.dds.servicioUsuario.models.entities;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
  @CollectionTable(name = "permisos", joinColumns =
  @JoinColumn(name = "usuario_id", referencedColumnName = "id"))
  private List<Permiso> permisos;
  @Column(name = "email")
  private String email;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "fecha_creacion", nullable = false)
  private LocalDate fechaCreacion;
  @Column(name = "provider_oauth")
  private String providerOAuth;

}
