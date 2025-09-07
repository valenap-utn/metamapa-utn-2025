package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.converters;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.Absoluta;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.AlgoritmoConsenso;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.MayoriaSimple;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.MultiplesMenciones;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.TodosConsensuados;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AlgoritmoConsensoConverter implements AttributeConverter<AlgoritmoConsenso, String> {

  @Override
  public String convertToDatabaseColumn(AlgoritmoConsenso algoritmoConsenso) {
    if (algoritmoConsenso == null) {
      return null;
    }
    if (algoritmoConsenso.getClass() == TodosConsensuados.class){
      return "TODOSCONSENSUADOS";
    } else if(algoritmoConsenso.getClass() == Absoluta.class){
      return "ABSOLUTA";
    } else if (algoritmoConsenso.getClass() == MayoriaSimple.class) {
      return "MAYORIASIMPLE";
    } else if (algoritmoConsenso.getClass() == MultiplesMenciones.class) {
      return "MULTIPLESMENCIONES";
    }
    return null;
  }

  @Override
  public AlgoritmoConsenso convertToEntityAttribute(String algoritmo) {
    if (algoritmo == null) {
      return null;
    }
    if (algoritmo.equalsIgnoreCase("TODOSCONSENSUADOS")) {
      return new TodosConsensuados();
    } else if(algoritmo.equalsIgnoreCase("ABSOLUTA")) {
      return new Absoluta();
    } else if (algoritmo.equalsIgnoreCase("MAYORIASIMPLE")) {
      return new MayoriaSimple();
    } else if (algoritmo.equalsIgnoreCase("MULTIPLESMENCIONES")) {
      return new MultiplesMenciones();
    }
    return null;
  }
}
