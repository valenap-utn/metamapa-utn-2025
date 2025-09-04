package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.time.LocalDateTime;
import lombok.Getter;

public abstract class FiltroPorFecha implements Filtro{
  @Getter
  private final LocalDateTime desde;
  @Getter
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
