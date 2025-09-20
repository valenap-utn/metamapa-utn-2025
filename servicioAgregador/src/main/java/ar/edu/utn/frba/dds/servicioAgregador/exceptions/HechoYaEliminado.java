package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

public class HechoYaEliminado extends AppError {
  public HechoYaEliminado(String mensaje) {
    super(mensaje, "HechoYaEliminado");
  }
}
