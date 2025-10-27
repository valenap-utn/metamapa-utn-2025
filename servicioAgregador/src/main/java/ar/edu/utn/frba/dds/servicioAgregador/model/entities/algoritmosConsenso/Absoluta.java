package ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.FuenteColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import java.util.Collection;

public class Absoluta implements AlgoritmoConsenso {
  public boolean consensuarHecho(Hecho hecho, Collection<FuenteColeccion> fuenteColeccions) {
    for(FuenteColeccion fuenteColeccion : fuenteColeccions){
      if(!fuenteColeccion.tieneHecho(hecho)){
        return false;
      }
    }
    return true; //si todas lo tienen => consensuado
  }

  @Override
  public String getNombre() {
    return "ABSOLUTA";
  }
}
