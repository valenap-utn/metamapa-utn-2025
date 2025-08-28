package ar.edu.utn.frba.dds.servicioFuenteEstatica.services;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoDTOEstatica;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface IHechoService {
  public Set<HechoDTOEstatica> importarDesdeCSV(MultipartFile archivo, Long idUsuario);

  public Set<HechoDTOEstatica> obtenerTodos();

  public HechoDTOEstatica marcarEliminadoHecho(Long idHecho);

  void eliminarTodo();
}
