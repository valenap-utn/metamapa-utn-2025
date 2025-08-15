package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Ubicacion;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class HechoDTODinamica  {
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDate fechaAcontecimiento;
  private LocalDate fechaCarga;
  private MultipartFile contenidoMultimedia;
  private String contenido;
  private boolean esAnonimo;
  private Long idUsuario;
}
