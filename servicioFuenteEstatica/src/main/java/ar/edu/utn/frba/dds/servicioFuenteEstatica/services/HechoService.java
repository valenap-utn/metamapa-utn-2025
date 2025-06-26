package ar.edu.utn.frba.dds.servicioFuenteEstatica.services;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.daos.IHechosDAO;
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
public class HechoService {
  private final IHechosDAO hechosDAO;
  private final IHechoRepository hechoRepository;

  public HechoService(IHechosDAO hechosDAO, IHechoRepository hechoRepository) {
    this.hechosDAO = hechosDAO;
    this.hechoRepository = hechoRepository;
  }

  //Importar desde CSV a Set<Hecho>
  public Set<Hecho> importarDesdeCSV(MultipartFile archivo) throws IOException {
    Set<HechoValueObject> hechosVO = hechosDAO.getAll(archivo.getInputStream());

    Set<Hecho> hechos = hechosVO.stream()
        .map(unHvo -> new Hecho(unHvo, Origen.PORDATASET))
        .collect(Collectors.toSet());

    hechoRepository.saveAll(hechos);
    return hechos;
  }

  public Set<Hecho> obtenerTodos() {
    return hechoRepository.findAll();
  }

  public Optional<Hecho> buscarPorID(String id) { return hechoRepository.findByID(id); }

  public Optional<Set<Hecho>> buscarPorTitulo(String titulo) {
    return hechoRepository.buscarPorTitulo(titulo);
  }

  public void eliminarTodo() {
    hechoRepository.clear();
  }

}
