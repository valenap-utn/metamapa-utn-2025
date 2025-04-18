package ar.edu.utn.frba.dds.domain.entities.colecciones.hechos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter

@Builder
public class HechoValueObject {
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDate fechaAcontecimiento;

  public HechoValueObject of(String titulo, String descripcion, LocalDate fechaAcontecimiento, Categoria categoria, Ubicacion ubicacion) {
    return HechoValueObject
            .builder()
            .titulo(titulo)
            .descripcion(descripcion)
            .fechaAcontecimiento(fechaAcontecimiento)
            .categoria(categoria)
            .ubicacion(ubicacion)
            .build();
  }

  public HechoValueObject(String titulo, String descripcion, Categoria categoria, Ubicacion ubicacion, LocalDate fechaAcontecimiento) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.fechaAcontecimiento = fechaAcontecimiento;
    this.ubicacion = ubicacion;
  }

  public boolean esIgualA(HechoValueObject unHecho) {
    return this.titulo.equalsIgnoreCase(unHecho.getTitulo());
  }

}
