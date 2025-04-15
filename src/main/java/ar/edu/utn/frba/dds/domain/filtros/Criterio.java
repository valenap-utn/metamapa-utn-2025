package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

import java.util.HashSet;
import java.util.Set;

public class Criterio {
    private Set<Filtro> filtrosUsados;

    public Criterio() {
        filtrosUsados = new HashSet<>();
    }

    public boolean filtarPorCriterio(Hecho hecho) {
        //TODO: el comportamiento de esta funcion
        return true; //esta puesto para que no de error nomas
    }
}
