package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoProxy;
import java.util.List;
import reactor.core.publisher.Mono;

public interface IColeccionService {
  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccion);
  public Mono<Void> actualizarHechosColecciones();

  public List<ColeccionDTOOutput> getAllColecciones();

  public ConjuntoHechoProxy getHechosPorColeccion(String idColeccion);
}
