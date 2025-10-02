package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos;

import lombok.Data;

@Data
public class SolicitudDTO {
  private Long id;
  private Long idHecho;
  private Long idusuario;
  private String justificacion;
  private String estado;
}
