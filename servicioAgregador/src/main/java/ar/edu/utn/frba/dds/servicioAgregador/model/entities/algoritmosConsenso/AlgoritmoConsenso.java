package ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.fuente.FuenteColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import java.util.Collection;


public interface AlgoritmoConsenso {
  boolean consensuarHecho(Hecho hecho, Collection<FuenteColeccion> fuenteColeccions);
}
