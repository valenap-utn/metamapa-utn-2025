package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos;


import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Ubicacion;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HechoDTOEstatica {
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private Long idUsuario;
  private String nombreArchivo;
}
