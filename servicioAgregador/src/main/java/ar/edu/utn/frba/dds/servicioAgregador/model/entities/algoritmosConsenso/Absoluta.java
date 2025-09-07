package ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.fuente.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import java.util.Collection;

public class Absoluta implements AlgoritmoConsenso {
  public boolean consensuarHecho(Hecho hecho, Collection<Fuente> fuentes) {
    for(Fuente fuente : fuentes){
      if(!fuente.tieneHecho(hecho)){
        return false;
      }
    }
    return true; //si todas lo tienen => consensuado
  }
}
