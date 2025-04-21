package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Filtro {
    public Set<Hecho> filtrarHechos(Set<Hecho> hechos){
        return hechos.stream().filter(
                hecho -> this.hechoCumple(hecho)
        ).collect(Collectors.toSet());
    }

    protected abstract boolean hechoCumple(Hecho unHecho);
}
