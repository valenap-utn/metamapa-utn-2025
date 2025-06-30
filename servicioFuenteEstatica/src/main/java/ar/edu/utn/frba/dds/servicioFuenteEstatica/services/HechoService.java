package ar.edu.utn.frba.dds.servicioFuenteEstatica.services;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.daos.IHechosDAO;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoDTOEstatica;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Origen;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.IHechoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HechoService implements IHechoService {
  private final IHechosDAO hechosDAO;
  private final IHechoRepository hechoRepository;

  public HechoService(IHechosDAO hechosDAO, IHechoRepository hechoRepository) {
    this.hechosDAO = hechosDAO;
    this.hechoRepository = hechoRepository;
  }

  //Importar desde CSV a Set<Hecho>
  public Set<HechoDTOEstatica> importarDesdeCSV(MultipartFile archivo) throws IOException {
    Set<HechoValueObject> hechosVO = hechosDAO.getAll(archivo.getInputStream());

    Set<Hecho> hechos = hechosVO.stream()
        .map(unHvo -> new Hecho(unHvo, Origen.PORDATASET))
        .collect(Collectors.toSet());

    hechoRepository.saveAll(hechos);
    return hechos.stream().map(this::toHechoDTOEstatica).collect(Collectors.toSet());
  }

  public Set<HechoDTOEstatica> obtenerTodos() {
    return hechoRepository.findAll().stream()
        .map(this::toHechoDTOEstatica)
        .collect(Collectors.toSet());
  }

  public HechoDTOEstatica marcarEliminadoHecho(Long idHecho) {
    Hecho hecho = this.hechoRepository.findByID(idHecho).orElse(null);
    if(hecho == null)
      return null;
    hecho.setEliminado(true);
    this.hechoRepository.saveHecho(hecho);
    return this.toHechoDTOEstatica(hecho);
  }

  public void eliminarTodo() {
    hechoRepository.clear();
  }

  public HechoDTOEstatica toHechoDTOEstatica(Hecho hecho) {
    HechoDTOEstatica dto = new HechoDTOEstatica();
    dto.setId(hecho.getId());
    dto.setTitulo(hecho.getTitulo());
    dto.setUbicacion(hecho.getUbicacion());
    dto.setDescripcion(hecho.getDescripcion());
    dto.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    dto.setFechaCarga(hecho.getFechaCarga());
    dto.setCategoria(hecho.getCategoria());
    return dto;
  }
}
