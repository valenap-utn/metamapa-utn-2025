package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.mappers;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Origen;

public class HechoMapper {

  public static HechoValueObject toValueObject(Hecho hecho) {
    return HechoValueObject.builder()
        .titulo(hecho.getTitulo())
        .descripcion(hecho.getDescripcion())
        .fechaAcontecimiento(hecho.getFechaAcontecimiento())
        .categoria(hecho.getCategoria())
        .ubicacion(hecho.getUbicacion())
        .build();
  }

  public static Hecho toEntity(HechoValueObject vo, Origen origen) {
    return new Hecho(vo, origen);
  }

}
