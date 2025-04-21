package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

import ar.edu.utn.frba.dds.utils.FoldlDeFunciones;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;


public class Criterio {
    private Set<Function<Set<Hecho>, Set<Hecho>>> filtros;

    public Criterio() {
        this.filtros = new HashSet<>();
        this.filtros.add(new FiltroNoEstaEliminado());
    }

    public void agregarCriterios(Filtro ... filtros) {
        this.filtros.addAll(List.of(filtros));
    }
    public Set<Hecho> getHechosFiltrados(Set<Hecho> hechos) {
        return FoldlDeFunciones.foldl(hechos, this.filtros);
    }
}
