package ar.edu.utn.frba.dds.servicioFuenteEstatica.services;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.daos.IHechosDAO;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.ColeccionHecho;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Origen;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.HechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.IHechoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HechoService {
  private final IHechosDAO hechosDAO;
  private final IHechoRepository hechoRepository;

  public HechoService(IHechosDAO hechosDAO, IHechoRepository hechoRepository) {
    this.hechosDAO = hechosDAO;
    this.hechoRepository = hechoRepository;
  }

  public ColeccionHecho importarDesdeCSV(MultipartFile archivo) throws IOException {
    Set<HechoValueObject> hechosVO = hechosDAO.getAll(archivo.getInputStream());

    ColeccionHecho coleccion = new ColeccionHecho();
    Set<Hecho> hechos = hechosVO.stream()
        .map(unHvo -> new Hecho(unHvo, Origen.PORDATASET, coleccion))
        .collect(Collectors.toSet());

    hechoRepository.saveAll(coleccion.getId(), hechos);
    return coleccion;
  }

  public Set<Hecho> obtenerTodos() {
    return hechoRepository.findAll();
  }

  public Optional<Set<Hecho>> buscarPorTitulo(String titulo) {
    return hechoRepository.buscarPorTitulo(titulo);
  }

  public Optional<Set<Hecho>> buscarPorColeccion(String idColeccion) {
    return hechoRepository.findByColeccion(idColeccion);
  }

  public void eliminarTodo() {
    hechoRepository.clear();
  }
}
