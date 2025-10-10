package ar.edu.utn.frba.dds.servicioUsuario.exceptions;

public class UsuarioNoEncontrado extends AppError {
  public UsuarioNoEncontrado(String s) {
    super(s, "UsuarioNoEncontrado");
  }
}
