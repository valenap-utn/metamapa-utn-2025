package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import lombok.Data;

@Data
public class UbicacionGobiernoDTO {
  private ProvinciaDTO provincia;
  private DepartamentoDTO departamento;
  private GobiernoLocalDTO gobierno_local;
}
