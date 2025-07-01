package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.TipoOrigen;
import lombok.Data;

@Data
public class FuenteDTO {
  private TipoOrigen tipoOrigen;
  private String url;
}
