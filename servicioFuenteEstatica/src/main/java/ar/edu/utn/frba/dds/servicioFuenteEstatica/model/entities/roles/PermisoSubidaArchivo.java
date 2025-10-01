package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SUBIDAARCHIVO")
@NoArgsConstructor
public class PermisoSubidaArchivo extends Permiso {
  public PermisoSubidaArchivo(String descripcion) {
    super(descripcion);
  }
}
