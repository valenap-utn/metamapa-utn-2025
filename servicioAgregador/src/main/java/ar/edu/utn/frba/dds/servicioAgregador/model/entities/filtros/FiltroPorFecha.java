package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.time.LocalDate;

public abstract class FiltroPorFecha implements Filtro{
  private LocalDate desde;
  private LocalDate hasta;

  public FiltroPorFecha(LocalDate desde, LocalDate hasta) {
    this.desde = desde;
    this.hasta = hasta;
  }

  @Override
  public boolean hechoCumple(Hecho hecho) {
    LocalDate fecha = obtenerUnTipoFecha(hecho);
    return (fecha.isEqual(desde) || fecha.isAfter(desde)) &&
        (fecha.isEqual(hasta) || fecha.isBefore(hasta));
  }

  protected abstract LocalDate obtenerUnTipoFecha(Hecho unHecho);
}
