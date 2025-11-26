package ar.edu.utn.frba.dds.metamapa_client.clients;

import ar.edu.utn.frba.dds.metamapa_client.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.HechoDTOInput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.SolicitudEdicionDTO;
import java.util.Collection;
import java.util.List;

public interface IFuenteDinamica {
  HechoDTOOutput crearHecho(HechoDTOInput hecho, String baseUrl);

  HechoDTOOutput actualizarHecho(HechoDTOInput hecho, String baseUrl);

  HechoDTOOutput revisarHecho(Long idHecho, RevisionDTO revisionDTO, String baseUrl);

  SolicitudEdicionDTO solicitarModificacion(HechoDTOInput hechoDtoInput, Long userId, String baseUrl);

  SolicitudEdicionDTO  procesarSolicitudEdicion(Long idSolicitud, String baseUrl, RevisionDTO revisionDTO);

  List<SolicitudEdicionDTO> findAllSolicitudesEdicion(String baseUrl);

  List<HechoDTOOutput> listHechosDelUsuario(Long userId, String baseUrl);

  HechoDTOOutput getHecho(Long idHecho, String urlFuenteDinamica);

  Collection<String> findAllCategorias(String urlFuenteDinamica);

  List<HechoDTOOutput> findHechosNuevos(String baseUrl, String estado);
}
