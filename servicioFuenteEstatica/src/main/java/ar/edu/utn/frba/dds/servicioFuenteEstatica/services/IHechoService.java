package ar.edu.utn.frba.dds.servicioFuenteEstatica.services;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoDTOEstatica;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public interface IHechoService {
  public Set<HechoDTOEstatica> importarDesdeCSV(MultipartFile archivo) throws IOException;

  public Set<HechoDTOEstatica> obtenerTodos();

  public HechoDTOEstatica marcarEliminadoHecho(Long idHecho);

  void eliminarTodo();
}
