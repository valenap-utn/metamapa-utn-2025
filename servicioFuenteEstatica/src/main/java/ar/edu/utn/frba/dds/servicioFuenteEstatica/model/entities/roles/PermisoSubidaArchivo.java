package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue("SUBIDAARCHIVO")
public class PermisoSubidaArchivo extends Permiso {
  public PermisoSubidaArchivo(String descripcion) {
    super(descripcion);
  }
}
