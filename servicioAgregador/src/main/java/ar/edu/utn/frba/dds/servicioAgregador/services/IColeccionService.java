package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import java.util.List;
import reactor.core.publisher.Mono;

public interface IColeccionService {
  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccion, String  algoritmo);
  public Mono<Void> actualizarHechosColecciones();

  public List<ColeccionDTOOutput> getAllColecciones();

  public ConjuntoHechoCompleto getHechosPorColeccion(String idColeccion, FiltroDTO filtro);
}
