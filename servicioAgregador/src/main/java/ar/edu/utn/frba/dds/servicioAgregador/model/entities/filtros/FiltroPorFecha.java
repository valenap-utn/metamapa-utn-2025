package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class FiltroPorFecha implements Filtro{
  private final LocalDateTime desde;
  private final LocalDateTime hasta;

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
