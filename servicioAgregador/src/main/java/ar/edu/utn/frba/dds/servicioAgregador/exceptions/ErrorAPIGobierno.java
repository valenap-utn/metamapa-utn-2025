package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

public class ErrorAPIGobierno extends AppError{
  public ErrorAPIGobierno(String serverError) {
    super(serverError, "ErrorAPIGobierno");
  }
}
