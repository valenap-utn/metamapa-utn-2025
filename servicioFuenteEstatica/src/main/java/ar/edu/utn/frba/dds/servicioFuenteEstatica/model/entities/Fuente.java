package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities;

import java.util.Set;

public interface Fuente {
  Set<Hecho> obtenerHechos();
}