package ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions;

public class ImportacionFallidaException extends RuntimeException {
  public ImportacionFallidaException(String mensaje, Throwable cause) {
    super(mensaje, cause);
  }
}
