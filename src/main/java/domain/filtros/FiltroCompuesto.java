package domain.filtros;

import ar.edu.utn.frba.dds.Hecho;

import java.util.HashSet;
import java.util.Set;

public class FiltroCompuesto implements Filtro {
    private Set<Filtro> filtros;

    public FiltroCompuesto(Filtro ... filtros) {
        this.filtros = new HashSet<>();
    }

    @Override
    public boolean hechoCumple(Hecho hecho) {
        return filtros.stream().allMatch(f -> f.hechoCumple(hecho));
    }
}

