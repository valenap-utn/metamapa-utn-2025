package ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.fuente.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import java.util.Collection;


public class MultiplesMenciones implements AlgoritmoConsenso {

  @Override
  public boolean consensuarHecho(Hecho hecho, Collection<Fuente> fuentes) {
    int coincidencias = 0;
    for(Fuente fuente : fuentes){
      if(fuente.tieneHecho(hecho)){
        coincidencias++;
      }
      if(fuente.tieneOtroHechoConMismoNombrePeroDistintosAtributos(hecho)){
        return false;
      }
    }
    return coincidencias >= 2;
  }
}
