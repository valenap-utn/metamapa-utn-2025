package ar.edu.utn.frba.dds.servicioFuenteProxy.exceptions;

public class HechoYaEliminado extends AppError{
  public HechoYaEliminado(String mensaje) {
    super(mensaje, "Hecho Ya Eliminado");
  }
}
