package ar.edu.utn.frba.dds.servicioUsuario.models.dtos;

import lombok.Data;

@Data
public class AuthResponseDTO {
  private String accessToken;
  private String refreshToken;
}
