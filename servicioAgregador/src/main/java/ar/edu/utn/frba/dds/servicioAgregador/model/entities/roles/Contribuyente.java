package ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;
import java.util.ArrayList;
import java.util.List;

@DiscriminatorValue("CONTRIBUYENTE")
public class Contribuyente extends Rol {

  private final List<Permiso> permisos;
  public Contribuyente() {
    this.permisos = new ArrayList<>();
    this.permisos.add(new PermisoCrearColeccion());
  }

  @Override
  public boolean tienePermisoDe(Permiso permiso) {
    return this.permisos.contains(permiso);
  }
}
