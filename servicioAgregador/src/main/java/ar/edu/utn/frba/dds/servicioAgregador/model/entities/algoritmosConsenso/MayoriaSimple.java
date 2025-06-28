package ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import java.util.List;

public class MayoriaSimple implements AlgoritmoConsenso {
  public boolean consensuarHecho(Hecho hecho, List<Fuente> fuentes) {
    int coincidencias = 0;
    for (Fuente fuente : fuentes) {
      if(fuente.tieneHecho(hecho)){
        coincidencias++;
      }
    }
    int cantidadFuentes = fuentes.size() ;

    return coincidencias >= Math.ceil(cantidadFuentes / 2.0);
  }
}
