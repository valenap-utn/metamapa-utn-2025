package ar.edu.utn.frba.dds.servicioUsuario.models.dtos;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class HechoDTOInput {
  private String titulo;
  private Long id;
  private String descripcion;
  private Categoria categoria = new Categoria();
  private Ubicacion ubicacion = new Ubicacion();
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private ContenidoMultimedia contenidoMultimedia = new ContenidoMultimedia();
  private String contenido;
  private UsuarioDTO usuario;
  private Long idUsuario;
}
