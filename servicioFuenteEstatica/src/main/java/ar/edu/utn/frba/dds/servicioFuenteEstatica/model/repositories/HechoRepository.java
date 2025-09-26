package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class HechoRepository implements IHechoRepository {
  private final AtomicLong idGenerador = new AtomicLong(1);
  private final Map<Long , Hecho> hechoPorID = new HashMap<>();

  @Override
  public void saveAll(Set<Hecho> setHechos) {
    setHechos.forEach(this::saveHecho);
  }

  @Override
  public void saveHecho(Hecho hecho) {
    Long id = hecho.getId();
    if(id == null) {
      id = idGenerador.getAndIncrement();
    }
    hechoPorID.put(id, hecho);
    hecho.setId(id);
  }


  @Override
  public Set<Hecho> findAll() {
    return new HashSet<>(hechoPorID.values()).stream()
        .filter(hecho -> !hecho.getEliminado()).collect(Collectors.toSet());
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
  public Optional<Hecho> findByID(Long id) {
    return Optional.ofNullable(hechoPorID.get(id));
  }

}