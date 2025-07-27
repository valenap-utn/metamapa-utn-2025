package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

public class SolicitudError extends AppError {
  public SolicitudError(String mensaje) {
    super(mensaje, "Solicitud Error");
  }
}
