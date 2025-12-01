package ar.edu.utn.frba.dds.servicioEstadistica.model.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Solicitud {
  private Long idHecho;
  private String justificacion;
  private Estado estado;
  private Long idUsuario;
  private Long id;

  public boolean fueMarcadaComoSpam() {
    return estado == Estado.SPAM;
  }
}
