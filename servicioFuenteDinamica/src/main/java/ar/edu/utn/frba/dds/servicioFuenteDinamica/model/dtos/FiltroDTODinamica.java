package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroDTODinamica {
  private Boolean pendientes;
  private Long idUsuario;
  private Boolean servicioAgregador;
  private Integer nroPagina;
}
