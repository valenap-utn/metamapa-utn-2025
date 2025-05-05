package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Filtro {
    protected boolean hechoCumple(Hecho unHecho);
}
