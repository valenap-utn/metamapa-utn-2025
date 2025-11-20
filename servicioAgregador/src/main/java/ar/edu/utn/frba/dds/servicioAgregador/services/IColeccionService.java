package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.UsuarioDTO;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface IColeccionService {
  ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccion);
  Mono<Void> actualizarHechosColecciones();

  List<ColeccionDTOOutput> getAllColecciones();

  ConjuntoHechoCompleto getHechosPorColeccion(UUID idColeccion, FiltroDTO filtro);

  ColeccionDTOOutput cambiarColeccion(ColeccionDTOInput coleccion, UUID idColeccion);

  ColeccionDTOOutput eliminarColeccion(UUID id, UsuarioDTO usuariodto);
}
