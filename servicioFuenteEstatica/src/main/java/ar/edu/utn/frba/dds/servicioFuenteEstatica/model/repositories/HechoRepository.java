package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class HechoRepository implements IHechoRepository {

  // Guardamos un conjunto de Hechos por colección
  private final Map<String, Set<Hecho>> hechos = new HashMap<>();

  @Override
  public void saveAll(UUID coleccionID, Set<Hecho> setHechos) {
    hechos.put(coleccionID.toString(), setHechos);
  }

  @Override
  public Set<Hecho> findAll() {
    return hechos.values().stream()
        .flatMap(set -> set.stream())
        .collect(Collectors.toSet());
  }

  @Override
  public Set<Hecho> findAll(String coleccionID) {
    return hechos.getOrDefault(coleccionID, Set.of());
  }

  @Override
  public void clear(String coleccionID) {
    hechos.remove(coleccionID);
  }

  @Override
  public void clear() {
    hechos.clear();
  }

  @Override
  public Optional<Set<Hecho>> buscarPorTitulo(String titulo) {
    Set<Hecho> encontrados = hechos.values().stream()
        .flatMap(set -> set.stream())
        .filter(h -> h.getTitulo().equalsIgnoreCase(titulo))
        .collect(Collectors.toSet());

    return encontrados.isEmpty() ? Optional.empty() : Optional.of(encontrados);
  }

  @Override
  public Optional<Set<Hecho>> findByColeccion(String idColeccion) {
    Set<Hecho> encontrados = hechos.getOrDefault(idColeccion, Set.of());
    return encontrados.isEmpty() ? Optional.empty() : Optional.of(encontrados);
  }
}