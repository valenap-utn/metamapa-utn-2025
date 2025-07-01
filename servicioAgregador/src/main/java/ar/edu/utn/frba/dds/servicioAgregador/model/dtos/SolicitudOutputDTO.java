package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import lombok.Data;

@Data
public class SolicitudOutputDTO {
  Long idHecho;
  Long idusuario;
  String justificacion;
}
