package ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CREARCOLECCION")
public class PermisoCrearColeccion extends Permiso{
  public PermisoCrearColeccion() {
    super("Permite crear una coleccion");
  }
}
