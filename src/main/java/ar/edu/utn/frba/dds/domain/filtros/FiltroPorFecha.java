package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

import java.time.LocalDateTime;

public abstract class FiltroPorFecha implements Filtro{
  private LocalDateTime desde;
  private LocalDateTime hasta;

  public FiltroPorFecha(LocalDateTime desde, LocalDateTime hasta) {
    this.desde = desde;
    this.hasta = hasta;
  }

  @Override
  public boolean hechoCumple(Hecho hecho) {
    LocalDateTime fecha = obtenerUnTipoFecha(hecho);
    return (fecha.isEqual(desde) || fecha.isAfter(desde)) &&
        (fecha.isEqual(hasta) || fecha.isBefore(hasta));
  }

  protected abstract LocalDateTime obtenerUnTipoFecha(Hecho unHecho);
}
