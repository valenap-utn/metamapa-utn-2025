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
    return switch (tipo) {
      case "MAYORIASIMPLE" -> new MayoriaSimple();
      case "ABSOLUTA" -> new Absoluta();
      case "MULTIPLESMENCIONES" -> new MultiplesMenciones();
      default -> new TodosConsensuados();
    };
  }
}
