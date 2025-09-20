package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public abstract class FiltroPorFecha extends Filtro{

  @Getter
  @Column(name = "fecha_desde")
  private  LocalDateTime desde;
  @Getter
  @Column(name = "fecha_hasta")
  private  LocalDateTime hasta;

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
