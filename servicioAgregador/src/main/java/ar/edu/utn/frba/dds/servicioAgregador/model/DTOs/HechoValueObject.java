package ar.edu.utn.frba.dds.servicioAgregador.model.DTOs;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import java.time.LocalDate;

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

  public static HechoValueObject of(String titulo, String descripcion, LocalDate fechaAcontecimiento, Categoria categoria, Ubicacion ubicacion) {
    return HechoValueObject
            .builder()
            .titulo(titulo)
            .descripcion(descripcion)
            .fechaAcontecimiento(fechaAcontecimiento)
            .categoria(categoria)
            .ubicacion(ubicacion)
            .build();
  }

  public HechoValueObject(String titulo, String descripcion, Categoria categoria,
                          Ubicacion ubicacion, LocalDate fechaAcontecimiento) {
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
