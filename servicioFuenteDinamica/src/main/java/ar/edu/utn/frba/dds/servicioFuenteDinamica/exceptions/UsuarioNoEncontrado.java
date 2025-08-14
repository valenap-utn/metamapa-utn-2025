package ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions;

public class UsuarioNoEncontrado extends AppError{
  public UsuarioNoEncontrado(String mensaje) {
    super(mensaje, "Usuario no encontrado");
  }
}
