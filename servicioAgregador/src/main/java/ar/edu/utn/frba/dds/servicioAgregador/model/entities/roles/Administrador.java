package ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Rol {

  @Override
  public boolean tienePermisoDe(Permiso permiso) {
    return true;
  }
}
