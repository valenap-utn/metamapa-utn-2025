package ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions;

public class HechoNoEncontrado extends AppError{
  public HechoNoEncontrado(String mensaje) {
    super(mensaje, "HechoNoEncontrado");
  }
}
