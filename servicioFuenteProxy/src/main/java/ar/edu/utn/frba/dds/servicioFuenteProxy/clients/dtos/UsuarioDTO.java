package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos;

import lombok.Data;

@Data
public class UsuarioDTO {
  private Long id;
  private String email;
  private String nombre;
  private String apellido;
}
