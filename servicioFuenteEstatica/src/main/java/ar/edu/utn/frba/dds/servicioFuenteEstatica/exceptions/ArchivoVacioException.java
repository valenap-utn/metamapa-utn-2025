package ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions;

public class ArchivoVacioException extends AppError {
  public ArchivoVacioException(String mensaje) {
    super(mensaje, "Archivo Vacio Error");
  }
}
