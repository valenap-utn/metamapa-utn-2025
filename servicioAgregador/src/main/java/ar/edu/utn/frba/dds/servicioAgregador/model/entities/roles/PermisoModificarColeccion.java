package ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue("MODIFICARCOLECCION")
public class PermisoModificarColeccion extends Permiso {
  public PermisoModificarColeccion() {
    super("");
  }
}
