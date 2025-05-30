package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ColeccionHecho {

  private final UUID id;
  private final LocalDate fechaCreacion;
  private final Set<Hecho> hechos;

  public ColeccionHecho() {
    this.id = UUID.randomUUID();
    this.fechaCreacion = LocalDate.now();
    this.hechos = new HashSet<>();
  }

  public void agregarHecho(Hecho hecho) {
    hechos.add(hecho);
  }

  public UUID getId() { return id; }

  public Set<Hecho> getHechos() {
    return Set.copyOf(hechos);
  }

}
