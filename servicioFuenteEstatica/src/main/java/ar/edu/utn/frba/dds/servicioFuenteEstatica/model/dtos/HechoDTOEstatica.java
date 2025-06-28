package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos;


import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Ubicacion;
import java.time.LocalDate;
import lombok.Data;

@Data
public class HechoDTOEstatica {
  private String id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDate fechaAcontecimiento;
  private LocalDate fechaCarga;
}
