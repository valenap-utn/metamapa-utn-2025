package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import java.time.LocalDate;
import lombok.Data;

@Data
public class HechoDTOProxy implements HechoDTO {
  private Long id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private Double latitud;
  private Double longitud;
  private LocalDate fechaAcontecimiento;
  private LocalDate fechaCarga;
  private String clientProxy;
  private Long idUsuario;

  @Override
  public Ubicacion getUbicacion() {
    return new Ubicacion(this.getLongitud().floatValue(), this.getLatitud().floatValue());
  }

  @Override
  public Categoria getCategoria() {
    return new Categoria(this.categoria);
  }
}
