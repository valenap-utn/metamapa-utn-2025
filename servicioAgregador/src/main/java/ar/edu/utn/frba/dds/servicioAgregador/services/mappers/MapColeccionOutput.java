package ar.edu.utn.frba.dds.servicioAgregador.services.mappers;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.FuenteDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import java.util.stream.Collectors;

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
    fuenteDTO.setId(fuente.getId());
    fuenteDTO.setTipo(fuente.getTipo());
    return fuenteDTO;
  }
}
