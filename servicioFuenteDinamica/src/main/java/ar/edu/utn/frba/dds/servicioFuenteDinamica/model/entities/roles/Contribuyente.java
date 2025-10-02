package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CONTRIBUYENTE")
public class Contribuyente extends Rol{
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable( name = "contribuyente_permisos",
            joinColumns = @JoinColumn(name = "contribuyente_id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id"))
    private final List<Permiso> permisos;

    public Contribuyente() {
        this.permisos = new ArrayList<>();
    }

    public void agregarPermisos(Permiso... permisos) {
        this.permisos.addAll(List.of(permisos));
    }
    @Override
    public boolean tienePermisoDe(Permiso permiso) {
        return this.permisos.stream().anyMatch(permiso1 -> permiso1.getClass() == permiso.getClass());
    }
}
