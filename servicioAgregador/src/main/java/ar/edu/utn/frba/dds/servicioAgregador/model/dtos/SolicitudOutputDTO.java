package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import lombok.Data;

@Data
public class SolicitudOutputDTO {
  Long id;
  Long idHecho;
  Long idusuario;
  String justificacion;
  String estado;
}
