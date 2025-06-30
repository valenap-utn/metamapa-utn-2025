package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;

import java.util.Optional;
import java.util.Set;

public interface IHechoRepository {
  // Para Hechos
  void saveAll(Set<Hecho> setHechos);

  void saveHecho(Hecho hecho);

  Set<Hecho> findAll(); // GET /hechos

  Optional<Set<Hecho>> buscarPorTitulo(String titulo);
  Optional<Hecho> findByID(Long id);

  void clear();

}
