package ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.HechoRepository;

public interface AlgoritmoConsenso {
  void consensuarHecho(Hecho hecho);
}
