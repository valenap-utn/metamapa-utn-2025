package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.converters;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.AlgoritmoConsenso;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AlgoritmoConsensoConverter implements AttributeConverter<AlgoritmoConsenso, String> {

  @Override
  public String convertToDatabaseColumn(AlgoritmoConsenso algoritmoConsenso) {

    return "";
  }

  @Override
  public AlgoritmoConsenso convertToEntityAttribute(String s) {

    return null;
  }
}
