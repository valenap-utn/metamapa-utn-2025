package ar.edu.utn.frba.dds.metamapa_client.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredencialesUserDTO {
  //  private String nombre;
  private String email;
  private String password;
}
