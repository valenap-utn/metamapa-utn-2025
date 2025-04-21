package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Criterio {
    private Set<Filtro> filtros;

    public Criterio() {
        filtros = new HashSet<>();
    }

    public void agregarCriterios(Filtro ... filtros) {
        this.filtros.addAll(List.of(filtros));
    }
    public boolean hechoCumple(Hecho hecho) {
        return filtros.stream().allMatch(f -> f.hechoCumple(hecho));
    }
}
