package domain.filtros;

import ar.edu.utn.frba.dds.Hecho;

public interface Filtro {
    boolean hechoCumple(Hecho hecho);
}
