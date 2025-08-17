package ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions;

import lombok.Getter;

public class AppError extends RuntimeException {
  @Getter
  private final String tipoError;

  public AppError(String mensaje, String tipoError) {
    super(mensaje);
    this.tipoError = tipoError;
  }
}
