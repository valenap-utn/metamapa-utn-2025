package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Embeddable
public class HechoModificacion {
  @Column(name = "hechomod_titulo", nullable = false)
  private String titulo;
  @Column(name = "hechomod_descripcion", nullable = false, columnDefinition = "TEXT")
  private String descripcion;
  @Column(name = "hechomod_categoria", nullable = false)
  private String categoria;
  @Embedded
  private Ubicacion ubicacion;
  @Column(name = "hechomod_fecha_acontimiento", nullable = false)
  private LocalDateTime fechaAcontecimiento;
  @Embedded
  private ContenidoMultimedia contenidoMultimedia;
  @Column(name = "hechomod_contenido")
  private String contenido;
}
