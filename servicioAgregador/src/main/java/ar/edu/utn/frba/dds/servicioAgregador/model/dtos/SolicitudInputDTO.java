package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SolicitudInputDTO {
  @NotNull
  private Long idHecho;

  @NotNull
  private Long idusuario;

  @NotBlank
  private String justificacion;
}
