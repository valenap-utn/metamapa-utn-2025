package ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.FuenteColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import java.util.Collection;


public class MayoriaSimple implements AlgoritmoConsenso {
  public boolean consensuarHecho(Hecho hecho, Collection<FuenteColeccion> fuenteColeccions) {
    int coincidencias = 0;
    for (FuenteColeccion fuenteColeccion : fuenteColeccions) {
      if(fuenteColeccion.tieneHecho(hecho)){
        coincidencias++;
      }
    }
    int cantidadFuentes = fuenteColeccions.size() ;

    return coincidencias >= Math.ceil(cantidadFuentes / 2.0);
  }
}
