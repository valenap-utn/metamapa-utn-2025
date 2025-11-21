package ar.edu.utn.frba.dds.metamapa_client.dtos;

import lombok.Data;

@Data
public class LoginRequestDTO {
  private String email;
  private String password;
}
