package ar.edu.utn.frba.dds.servicioFuenteEstatica.services;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoDTOEstatica;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.UsuarioDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface IHechoService {
  Set<HechoDTOEstatica> importarDesdeCSV(MultipartFile archivo, UsuarioDTO usuario);

  Set<HechoDTOEstatica> obtenerTodos();

  HechoDTOEstatica marcarEliminadoHecho(Long idHecho);

  void eliminarTodo();
}
