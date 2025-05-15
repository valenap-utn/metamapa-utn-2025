package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import java.util.Set;

public interface Fuente {
    Set<Hecho> obtenerHechos();
}

