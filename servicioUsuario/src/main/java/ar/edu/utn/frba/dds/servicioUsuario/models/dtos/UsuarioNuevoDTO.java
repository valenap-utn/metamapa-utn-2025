package ar.edu.utn.frba.dds.servicioUsuario.models.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UsuarioNuevoDTO {
  private String nombre;
  private String apellido;
  private LocalDate fechaDeNacimiento;
  private String email;
  private String rolSolicitado;
  private String providerOAuth;
  private String password;
}
