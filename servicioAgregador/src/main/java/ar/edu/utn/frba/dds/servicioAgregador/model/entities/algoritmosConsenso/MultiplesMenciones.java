package ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.HechoRepository;

public class MultiplesMenciones implements AlgoritmoConsenso {
  public void consensuarHecho(Hecho hecho ) {
//  Nos fijamos si el Hecho se encuentra en HechoRepository
//    (podriamos hacer algun filter o algo parecido, como para después poder hacer un
//      size() de los filtrados)
//    Si el size es >= 2
//                        => el Hecho es Consensuado
  }
}
