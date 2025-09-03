package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input;

import ar.edu.utn.frba.dds.servicioFuenteProxy.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteProxy.model.entities.Ubicacion;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HechoDTOMetamapa  {
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private Long idUsuario;

  public Double getLongitud(){
    return ubicacion == null ? null : Double.valueOf(ubicacion.getLongitud());
  }

  public Double getLatitud(){
    return ubicacion == null ? null : Double.valueOf(ubicacion.getLatitud());
  }

  public String getNombreCategoria(){
    return categoria == null ? null : categoria.getNombre();
  }
}
