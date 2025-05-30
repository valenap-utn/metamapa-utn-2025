package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class HechoRepository implements IHechoRepository {

  private final Map<String , Hecho> hechoPorID = new HashMap<>();

  @Override
  public void saveAll(Set<Hecho> setHechos) {
    setHechos.forEach(hecho -> hechoPorID.put(hecho.getID(), hecho));
  }

  @Override
  public Set<Hecho> findAll() {
    return new HashSet<>(hechoPorID.values());
  }

  @Override
  public void clear() {
    hechoPorID.clear();
  }

  @Override
  public Optional<Set<Hecho>> buscarPorTitulo(String titulo) {
    Set<Hecho> encontrados = hechoPorID.values().stream()
        .filter(h -> h.getTitulo().equalsIgnoreCase(titulo))
        .collect(Collectors.toSet());

    return encontrados.isEmpty() ? Optional.empty() : Optional.of(encontrados);
  }

  @Override
  public Optional<Hecho> findByID(String id) {
    return Optional.ofNullable(hechoPorID.get(id));
  }



  // Guardamos un conjunto de Hechos por colección
//  private final Map<String, Set<Hecho>> hechos = new HashMap<>();

  //  @Override
//  public void saveAll(UUID coleccionID, Set<Hecho> setHechos) {
//    hechos.put(coleccionID.toString(), setHechos);
//  }

  //  @Override
//  public Set<Hecho> findAll(String coleccionID) {
//    return hechos.getOrDefault(coleccionID, Set.of());
//  }

//  @Override
//  public void clear(String coleccionID) {
//    hechos.remove(coleccionID);
//  }

//  @Override
//  public Optional<Set<Hecho>> findByColeccion(String idColeccion) {
//    Set<Hecho> encontrados = hechos.getOrDefault(idColeccion, Set.of());
//    return encontrados.isEmpty() ? Optional.empty() : Optional.of(encontrados);
//  }

}