package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

public class SolicitudNoEncontrada extends AppError {
  public SolicitudNoEncontrada(String mensaje) {
    super(mensaje, "Solicitud no encontrada");
  }
}
