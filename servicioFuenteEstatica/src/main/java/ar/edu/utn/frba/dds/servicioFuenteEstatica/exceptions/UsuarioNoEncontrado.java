package ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions;

public class UsuarioNoEncontrado extends AppError{
  public UsuarioNoEncontrado(String mensaje) {
    super(mensaje, "Usuario no encontrado");
  }
}
