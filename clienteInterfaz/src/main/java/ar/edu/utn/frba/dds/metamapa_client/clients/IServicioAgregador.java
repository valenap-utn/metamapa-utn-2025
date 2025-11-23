package ar.edu.utn.frba.dds.metamapa_client.clients;

import ar.edu.utn.frba.dds.metamapa_client.dtos.Categoria;
import ar.edu.utn.frba.dds.metamapa_client.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.SolicitudEdicionDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.SolicitudEliminacionDTO;

import java.util.List;
import java.util.UUID;

/**
 * TODO
 * Agrego todos estos métodos para poder levantar el módulo y chequear que funciona bien
 * la conexión clienteInterfaz <-> servicioAgregador
 * Después revisaremos bien a quien le pertenece cada cosa
 */

public interface IServicioAgregador {
  // Hechos
  List<HechoDTOOutput> findAllHechos(FiltroDTO filtro);
  List<HechoDTOOutput> findHechosByColeccionId(UUID coleccionId, FiltroDTO filtro);
  HechoDTOOutput getHecho(Long idHecho);
  List<HechoDTOOutput> listHechosDelUsuario(Long userId);

  //Solicitudes de Eliminacion
  List<SolicitudEliminacionDTO> findAllSolicitudes();
  SolicitudEliminacionDTO crearSolicitud(SolicitudEliminacionDTO solicitudEliminacionDTO);
  SolicitudEliminacionDTO cancelarSolicitud(Long idSolicitud);
  SolicitudEliminacionDTO aceptarSolicitud(Long idSolicitud);

  //Colecciones
  ColeccionDTOOutput modificarColeccion(ColeccionDTOInput coleccionDTOInput, UUID coleccionId);
  ColeccionDTOOutput eliminarColeccion(UUID idColeccion);
  ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccion);
  ColeccionDTOOutput revisarColeccion(UUID idColeccion);
  ColeccionDTOOutput actualizarColeccion(ColeccionDTOInput coleccion, UUID idColeccion);
  List<ColeccionDTOOutput> findColecciones();

  //Nuevos hechos
  HechoDTOOutput aprobarHecho(Long idHecho);
  HechoDTOOutput rechazarHecho(Long idHecho);

  //Solicitudes de Edicion
  List<SolicitudEdicionDTO> findAllSolicitudesEdicion();

  SolicitudEdicionDTO procesarSolicitudEdicion(Long idSolicitud, String baseUrl, RevisionDTO revisionDTO);

  //Info de usuario
  String getNombreUsuario(Long id);
  List<String> findAllCategorias();
}
