package ar.edu.utn.frba.dds.clienteInterfaz.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCrearRequestDTO {
  private String nombre;
  private String apellido;
  private String email;
  private String password;
  private String providerOAuth;
  private String rolSolicitado;
}
