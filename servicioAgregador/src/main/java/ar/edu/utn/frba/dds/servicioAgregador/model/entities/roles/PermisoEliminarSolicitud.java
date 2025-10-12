package ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ELIMINARSOLICITUD")
public class PermisoEliminarSolicitud extends Permiso {
}
