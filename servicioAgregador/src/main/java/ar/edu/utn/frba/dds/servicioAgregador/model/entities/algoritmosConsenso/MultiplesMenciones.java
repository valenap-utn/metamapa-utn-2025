package ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.FuenteColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import java.util.Collection;


public class MultiplesMenciones implements AlgoritmoConsenso {

  @Override
  public boolean consensuarHecho(Hecho hecho, Collection<FuenteColeccion> fuenteColeccions) {
    int coincidencias = 0;
    for(FuenteColeccion fuenteColeccion : fuenteColeccions){
      if(fuenteColeccion.tieneHecho(hecho)){
        coincidencias++;
      }
      if(fuenteColeccion.tieneOtroHechoConMismoNombrePeroDistintosAtributos(hecho)){
        return false;
      }
    }
    return coincidencias >= 2;
  }
}
