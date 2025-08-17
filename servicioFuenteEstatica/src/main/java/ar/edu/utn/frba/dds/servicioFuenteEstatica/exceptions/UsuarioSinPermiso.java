package ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions;

public class UsuarioSinPermiso extends AppError{

  public UsuarioSinPermiso(String mensaje) {
    super(mensaje, "Usuario Sin Permiso");
  }
}
