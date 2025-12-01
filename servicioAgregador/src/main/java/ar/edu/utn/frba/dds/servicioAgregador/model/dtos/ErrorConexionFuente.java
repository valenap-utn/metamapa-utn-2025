package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.AppError;

public class ErrorConexionFuente extends AppError {
  public ErrorConexionFuente(String s) {
    super(s, "Error al conectar con las fuentes");
  }
}
