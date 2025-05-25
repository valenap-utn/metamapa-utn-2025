package ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles;

import java.util.ArrayList;
import java.util.List;

public class Contribuyente implements Rol {
  private List<Permiso> permisos;
  public Contribuyente() {
    this.permisos = new ArrayList<Permiso>();
    this.permisos.add(new PermisoCrearColeccion());
  }

  @Override
  public boolean tienePermisoDe(Permiso permiso) {
    return this.permisos.contains(permiso);
  }
}
