package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

public class HechoNoEncontrado extends AppError {
  public HechoNoEncontrado(String mensaje) {
    super(mensaje, "Hecho no encontrado");
  }
}
