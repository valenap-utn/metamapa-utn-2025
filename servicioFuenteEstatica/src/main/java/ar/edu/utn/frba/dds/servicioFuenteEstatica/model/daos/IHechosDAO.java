package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.daos;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;

import java.io.InputStream;
import java.util.Set;

public interface IHechosDAO {
  Set<HechoValueObject> getAll(InputStream inputStream);
}
