package ar.edu.utn.frba.dds.clienteInterfaz.clients;

import ar.edu.utn.frba.dds.clienteInterfaz.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.SolicitudEdicionDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.SolicitudEliminacionDTO;

import jakarta.servlet.http.HttpSession;
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
  ConjuntoHechoDTO findAllHechos(FiltroDTO filtro);
  ConjuntoHechoDTO findHechosByColeccionId(UUID coleccionId, FiltroDTO filtro);
  HechoDTOOutput getHecho(Long idHecho);
  List<HechoDTOOutput> listHechosDelUsuario(Long userId);

  //Solicitudes de Eliminacion
  List<SolicitudEliminacionDTO> findAllSolicitudes();
  SolicitudEliminacionDTO cancelarSolicitud(Long idSolicitud, RevisionDTO revisionDTO);
  SolicitudEliminacionDTO aceptarSolicitud(Long idSolicitud, RevisionDTO revisionDTO);

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

  SolicitudEliminacionDTO crearSolicitud(Long idHecho, String justificacion, HttpSession session);

  Long getCantidadHechos();

  long getCantidadFuentes();
}
