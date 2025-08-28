package ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions;

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
