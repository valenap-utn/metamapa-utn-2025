package ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import java.util.List;

public class TodosConsensuados implements AlgoritmoConsenso {

  @Override
  public boolean consensuarHecho(Hecho hecho, List<Fuente> fuentes) {
    return true;
  }
}
