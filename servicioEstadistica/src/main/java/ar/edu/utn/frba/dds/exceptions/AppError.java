package ar.edu.utn.frba.dds.exceptions;

import lombok.Getter;

public class AppError extends RuntimeException{
  @Getter
  String tipoError;
  public AppError(String mensaje, String tipoError) {
    super(mensaje);
    this.tipoError = tipoError;
  }
}
