package ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue("CREARCOLECCION")
public class PermisoCrearColeccion extends Permiso{
  public PermisoCrearColeccion() {
    super("Permite crear una coleccion");
  }
}
