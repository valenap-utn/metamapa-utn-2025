package ar.edu.utn.frba.dds.domain.entities.colecciones.hechos;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter

@Builder
public class HechoValueObject {
  private String titulo;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;

  public HechoValueObject of(String titulo, LocalDateTime fechaAcontecimiento, Categoria categoria, Ubicacion ubicacion) {
    return HechoValueObject
        .builder()
        .titulo(titulo)
        .fechaAcontecimiento(fechaAcontecimiento)
        .categoria(categoria)
        .ubicacion(ubicacion)
        .build();
  }

  public boolean esIgualA(HechoValueObject unHecho) {
    return this.titulo.equalsIgnoreCase(unHecho.getTitulo());
  }

}
