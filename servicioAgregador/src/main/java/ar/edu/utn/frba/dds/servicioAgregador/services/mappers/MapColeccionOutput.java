package ar.edu.utn.frba.dds.servicioAgregador.services.mappers;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FuenteDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MapColeccionOutput {

  public ColeccionDTOOutput toColeccionDTOOutput(Coleccion coleccion) {
    ColeccionDTOOutput coleccionDTOOutput = new ColeccionDTOOutput();
    coleccionDTOOutput.setId(coleccion.getId());
    coleccionDTOOutput.setNombre(coleccion.getDescripcion());
    coleccionDTOOutput.setDescripcion(coleccion.getDescripcion());
    coleccionDTOOutput.setFuentes(coleccion.getFuentes().stream().map(this::toFuenteDTO).collect(Collectors.toList()));
    return coleccionDTOOutput;
  }

  private FuenteDTO toFuenteDTO(Fuente fuente) {
    FuenteDTO fuenteDTO = new FuenteDTO();
    fuenteDTO.setTipoOrigen(fuente.getOrigen().getTipo());
    fuenteDTO.setUrl(fuente.getOrigen().getUrl());
    return fuenteDTO;
  }
}
