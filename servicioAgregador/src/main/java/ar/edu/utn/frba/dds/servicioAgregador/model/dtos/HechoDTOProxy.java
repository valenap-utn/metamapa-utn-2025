package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HechoDTOProxy implements HechoDTO {
  private Long id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private Double latitud;
  private Double longitud;
  private LocalDateTime fecha;
  private LocalDateTime fechaCarga;
  private String fuente;
  private UsuarioDTO usuario;

  @Override
  public LocalDateTime getFechaAcontecimiento() {
    return this.fecha;
  }

  @Override
  public Ubicacion getUbicacion() {
    Float longitudUsada = this.longitud == null ? null : this.longitud.floatValue();
    Float latitudUsada = this.latitud == null ? null : this.latitud.floatValue();
    return new Ubicacion(longitudUsada, latitudUsada);
  }

  @Override
  public Categoria getCategoria() {
    return new Categoria(this.categoria);
  }
}
