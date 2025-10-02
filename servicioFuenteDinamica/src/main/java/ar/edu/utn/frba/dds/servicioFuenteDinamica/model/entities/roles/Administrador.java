package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Rol{
  @Override
  public boolean tienePermisoDe(Permiso permiso) {
    return true;
  }
}
