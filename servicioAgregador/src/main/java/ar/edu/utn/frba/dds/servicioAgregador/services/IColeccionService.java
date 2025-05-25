package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoValueObject;
import java.util.List;

public interface IColeccionService {
  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccion);
  public void actualizarHechosColecciones();

  public List<ColeccionDTOOutput> getAllColecciones();

  public List<HechoValueObject> getHechosPorColeccion(Long idColeccion);
}
