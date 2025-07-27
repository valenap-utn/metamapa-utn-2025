package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

public class UsuarioNoEncontrado extends AppError{
  public UsuarioNoEncontrado(String mensaje) {
    super(mensaje, "Usuario no encontrado");
  }
}
