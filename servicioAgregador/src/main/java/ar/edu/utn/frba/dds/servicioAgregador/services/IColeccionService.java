package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import java.util.List;
import reactor.core.publisher.Mono;

public interface IColeccionService {
  ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccion);
  Mono<Void> actualizarHechosColecciones();

  List<ColeccionDTOOutput> getAllColecciones();

  ConjuntoHechoCompleto getHechosPorColeccion(String idColeccion, FiltroDTO filtro);

  ColeccionDTOOutput cambiarColeccion(ColeccionDTOInput coleccion, String idColeccion);

  ColeccionDTOOutput eliminarColeccion(String id);
}
