package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles;

import java.util.ArrayList;
import java.util.List;

public class Contribuyente implements Rol {
  private final List<Permiso> permisos;
  public Contribuyente() {
    this.permisos = new ArrayList<>();
  }

  @Override
  public boolean tienePermisoDe(Permiso permiso) {
    return this.permisos.contains(permiso);
  }
}
