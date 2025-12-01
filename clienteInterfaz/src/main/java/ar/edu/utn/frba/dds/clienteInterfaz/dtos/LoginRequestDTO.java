package ar.edu.utn.frba.dds.clienteInterfaz.dtos;

import lombok.Data;

@Data
public class LoginRequestDTO {
  private String email;
  private String password;
}
