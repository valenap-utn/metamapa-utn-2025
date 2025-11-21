package ar.edu.utn.frba.dds.servicioUsuario.models.dtos;

import lombok.Data;

@Data
public class DatoEstadisticoDTO {
  private String primerCriterio;
  private String segundoCriterio;
  private Integer hora;
  private Long cantidad;
  private Double porcentaje;
  private Long total;
}
