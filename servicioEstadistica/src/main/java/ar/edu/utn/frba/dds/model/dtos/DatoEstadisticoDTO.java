package ar.edu.utn.frba.dds.model.dtos;

import lombok.Data;

@Data
public class DatoEstadisticoDTO {
  private String primerCriterio;
  private String segundoCriterio;
  private Long hora;
  private Long cantidad;
  private Double porcentaje;
  private Long total;
}
