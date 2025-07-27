package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

public class ErrorDTO {
  String mensaje;
  String tipoError;

  public ErrorDTO(String message, String tipoError) {
    this.mensaje = message;
    this.tipoError = tipoError;
  }
}
