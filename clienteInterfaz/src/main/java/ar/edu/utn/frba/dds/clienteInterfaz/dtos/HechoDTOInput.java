package ar.edu.utn.frba.dds.clienteInterfaz.dtos;

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
  private Long idUsuario;
  private MultipartFile contenidoMultimediaFile;

  public HechoDTOInputMultipart getMultipart() {
    return HechoDTOInputMultipart.builder()
            .titulo(titulo).id(id)
            .descripcion(descripcion)
            .categoria(categoria).ubicacion(ubicacion)
            .fechaAcontecimiento(fechaAcontecimiento).fechaCarga(fechaCarga)
            .contenido(contenido)
            .contenidoMultimedia(contenidoMultimedia)
            .idUsuario(idUsuario)
            .build();
  }
}
