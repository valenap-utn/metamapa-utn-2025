package ar.edu.utn.frba.dds.model.dtos;

import lombok.Data;

@Data
public class SolicitudDTO {
  Long id;
  Long idHecho;
  Long idusuario;
  String justificacion;
  String estado;
}
