package ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions;

public class ArchivoYaExiste extends AppError {
  public ArchivoYaExiste(String mensaje) {
    super(mensaje, "Archivo Ya Existe");
  }
}
