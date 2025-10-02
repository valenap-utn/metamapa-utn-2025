package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REVISARHECHO")
public class PermisoRevisarHecho extends Permiso {
}
