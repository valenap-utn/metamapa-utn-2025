package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Filtro implements Function<Set<Hecho>, Set<Hecho>> {
    public Set<Hecho> apply(Set<Hecho> hechos){
        return hechos.stream().filter(
                this::hechoCumple
        ).collect(Collectors.toSet());
    }

    protected abstract boolean hechoCumple(Hecho unHecho);
}
