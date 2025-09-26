package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import lombok.Data;

@Data
public class SolicitudOutputDTO {
  private Long id;
  private Long idHecho;
  private Long idusuario;
  private String justificacion;
  private String estado;
}
