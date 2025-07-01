package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.Absoluta;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.AlgoritmoConsenso;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.MayoriaSimple;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.MultiplesMenciones;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.TodosConsensuados;
import org.springframework.stereotype.Component;

@Component
public class FactoryAlgoritmo {
  public AlgoritmoConsenso getAlgoritmo(String tipo) {
    if(tipo.equals("mayoriaSimple"))
      return new MayoriaSimple();
    else if (tipo.equals("absoluta"))
      return new Absoluta();
    else if (tipo.equals("multiplesMenciones"))
      return new MultiplesMenciones();
    else
      return new TodosConsensuados();
  }
}
