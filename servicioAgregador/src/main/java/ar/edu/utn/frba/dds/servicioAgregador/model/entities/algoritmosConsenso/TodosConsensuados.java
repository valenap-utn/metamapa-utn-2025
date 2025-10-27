package ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.FuenteColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import java.util.Collection;


public class TodosConsensuados implements AlgoritmoConsenso {

  @Override
  public boolean consensuarHecho(Hecho hecho, Collection<FuenteColeccion> fuenteColeccions) {
    return true;
  }

  @Override
  public String getNombre() {
    return "TODOSCONSENSUADOS";
  }
}
