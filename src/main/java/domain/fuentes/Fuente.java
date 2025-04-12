package domain.fuentes;

import ar.edu.utn.frba.dds.Hecho;

import java.util.Set;

public interface Fuente {
    Set<Hecho> obtenerHechos();
}

