package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface IHechoRepository {
  Set<Hecho> findAll(); // GET /hechos
  Set<Hecho> findAll(String coleccionID); // GET /colecciones/:identificador/hechos
  void saveAll(UUID coleccionID, Set<Hecho> hechos);

  Optional<Set<Hecho>> buscarPorTitulo(String titulo);
  Optional<Set<Hecho>> findByColeccion(String idColeccion);

  void clear(String coleccionID);
  void clear();

}
