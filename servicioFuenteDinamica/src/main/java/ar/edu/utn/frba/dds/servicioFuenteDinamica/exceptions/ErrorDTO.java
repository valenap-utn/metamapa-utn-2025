package ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions;

public class ErrorDTO {
  String mensaje;
  String tipoError;

  public ErrorDTO(String message, String tipoError) {
    this.mensaje = message;
    this.tipoError = tipoError;
  }
}
