package ar.edu.utn.frba.dds.metamapa_client.dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HechoDTOInputMultipart {
  private String titulo;
  private Long id;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private ContenidoMultimedia contenidoMultimedia;
  private String contenido;
  private Long idUsuario;
}
