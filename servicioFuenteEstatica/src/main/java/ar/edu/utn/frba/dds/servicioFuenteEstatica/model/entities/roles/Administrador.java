package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Rol {

  @Override
  public boolean tienePermisoDe(Permiso permiso) {
    return true;
  }
}
