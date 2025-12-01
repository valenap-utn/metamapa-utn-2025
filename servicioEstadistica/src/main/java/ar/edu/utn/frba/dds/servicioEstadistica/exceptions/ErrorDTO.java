package ar.edu.utn.frba.dds.servicioEstadistica.exceptions;

import lombok.Data;

@Data
public class ErrorDTO {
  String mensaje;
  String tipoError;

  public ErrorDTO(String message, String tipoError) {
    this.mensaje = message;
    this.tipoError = tipoError;
  }
}
