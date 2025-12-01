package ar.edu.utn.frba.dds.clienteInterfaz.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DatoEstadisticoDTO {
  private String primerCriterio;      // provincia o categoría
  private String segundoCriterio;    // colección en el caso provincia-colección
  private String hora;
  private Long cantidad;
  private Double porcentaje;
  private Long total;

}
