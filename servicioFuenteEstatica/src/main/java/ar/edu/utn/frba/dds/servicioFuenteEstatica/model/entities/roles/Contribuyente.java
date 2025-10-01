package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CONTRIBUYENTE")
public class Contribuyente extends Rol {
  @ManyToMany
  @JoinTable( name = "contribuyente_permisos",
              joinColumns = @JoinColumn(name = "contribuyente_id"),
              inverseJoinColumns = @JoinColumn(name = "permiso_id"))
  private final List<Permiso> permisos;

  public Contribuyente() {
    this.permisos = new ArrayList<>();
  }

  @Override
  public boolean tienePermisoDe(Permiso permiso) {
    return this.permisos.contains(permiso);
  }
}
