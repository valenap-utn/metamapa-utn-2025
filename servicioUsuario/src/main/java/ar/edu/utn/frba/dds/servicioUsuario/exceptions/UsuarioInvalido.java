package ar.edu.utn.frba.dds.servicioUsuario.exceptions;

public class UsuarioInvalido extends AppError {
  public UsuarioInvalido(String s) {
    super(s, "UsuarioInvalido");
  }
}
