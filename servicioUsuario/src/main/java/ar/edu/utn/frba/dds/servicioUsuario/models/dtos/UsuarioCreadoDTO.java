package ar.edu.utn.frba.dds.servicioUsuario.models.dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UsuarioCreadoDTO {
  private String email;
  private LocalDate fechaCreacion;

  //Campos nuevos para simplificar unas cosiñas
  private Long id;
  private String nombre;
  private String apellido;
  private LocalDate fechaDeNacimiento;
  private String rol;
}
