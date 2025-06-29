package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

public class SolicitudError extends RuntimeException {
  public SolicitudError(String mensaje) {
    super(mensaje);
  }
}
