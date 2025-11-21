package ar.edu.utn.frba.dds.metamapa_client.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class UsuarioNuevoDTO {
  private String nombre;
  private String apellido;
  private String email;
  private LocalDate fechaDeNacimiento;
  private String password;
  private String rolSolicitado;
  private String providerOAuth;
}

