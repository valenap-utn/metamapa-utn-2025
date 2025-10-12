package ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CONTRIBUYENTE")
public class PermisoContribuyente extends Permiso {
}
