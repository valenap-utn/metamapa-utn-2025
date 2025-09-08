package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolicitudInputDTO {
  @NotNull
  private Long idHecho;

  private Long idusuario;

  @NotBlank
  private String justificacion;
}
