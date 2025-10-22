package ar.edu.utn.frba.dds.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Hecho {
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private Set<String> etiquetas;
  private boolean eliminado;
  private ContenidoMultimedia contenidoMultimedia;
  private Long idUsuario;
  private Origen origen;
  private List<String> titulosColeccion = new ArrayList<>();

  public void agregarTituloColeccion(String titulo) {
    this.titulosColeccion.add(titulo);
  }
}
