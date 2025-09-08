package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.converters;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Administrador;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Contribuyente;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Rol;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class rolConverter implements AttributeConverter<Rol, String> {

  @Override
  public String convertToDatabaseColumn(Rol rol) {
    if (rol == null) {
      return null;
    } else if (rol.getClass() == Administrador.class) {
      return "ADMINISTRADOR";
    } else if (rol.getClass() == Contribuyente.class) {
      return "CONTRIBUYENTE";
    }
    return null;
  }

  @Override
  public Rol convertToEntityAttribute(String rolString) {
    if (rolString == null) {
      return null;
    }
    if(rolString.equals("ADMINISTRADOR")){
      return new Administrador();
    } else if (rolString.equals("CONTRIBUYENTE")){
      return new Contribuyente();
    }
    return null;
  }
}
