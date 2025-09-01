package ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions;

public class SolicitudNoEncontrada extends AppError {
  public SolicitudNoEncontrada(String mensaje) {
    super(mensaje, "Solicitud no encontrada");
  }
}
