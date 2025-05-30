package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface IHechoRepository {
  // Para Hechos
  void saveAll(Set<Hecho> hechos);

  Set<Hecho> findAll(); // GET /hechos

  Optional<Set<Hecho>> buscarPorTitulo(String titulo);
  Optional<Hecho> findByID(String id);

  void clear();


  //Para Coleccion de Hechos
  //  void saveAll(UUID coleccionID, Set<Hecho> hechos);
  //  Set<Hecho> findAll(String coleccionID); // GET /colecciones/:identificador/hechos
  //  Optional<Set<Hecho>> findByColeccion(String idColeccion); //(my error)
  //  void clear(String coleccionID);
}
