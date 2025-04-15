package ar.edu.utn.frba.dds.domain.fuentes;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

import java.util.Set;

public interface Fuente {
    Set<Hecho> obtenerHechos();
}

