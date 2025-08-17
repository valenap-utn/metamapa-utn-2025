package ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions;

public class HechoNoEncontrado extends AppError {
  public HechoNoEncontrado(String mensaje) {
    super(mensaje, "Hecho no encontrado");
  }
}
