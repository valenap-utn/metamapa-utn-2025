package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import lombok.Data;

@Data
public class SolicitudInputDTO {
  Long idHecho;
  Long idusuario;
  String justificacion;
}
