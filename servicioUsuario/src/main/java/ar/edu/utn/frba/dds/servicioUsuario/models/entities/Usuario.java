package ar.edu.utn.frba.dds.servicioUsuario.models.entities;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {
  private Long id;
  private String nombre;
  private String apellido;
  private LocalDate fechaDeNacimiento;
  private Rol rol;
  private List<Permiso> permisos;
  private String email;
  
}
