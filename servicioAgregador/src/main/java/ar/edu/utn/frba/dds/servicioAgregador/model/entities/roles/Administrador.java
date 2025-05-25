package ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles;

public class Administrador implements Rol {

  @Override
  public boolean tienePermisoDe(Permiso permiso) {
    return true;
  }
}
