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
    setHechos.forEach(hecho -> hechoPorID.put(hecho.getId(), hecho));
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

}