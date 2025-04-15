package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

public interface Filtro {
    public boolean hechoCumple(Hecho hecho);
}
