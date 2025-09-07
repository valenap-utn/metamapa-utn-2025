package ar.edu.utn.frba.dds.servicioAgregador.services.mappers;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FuenteDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.fuente.FuenteColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.Filtro;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroPorCategoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroPorDescripcion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroPorFechaAcontecimiento;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroPorFechaCarga;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroPorTitulo;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroUbicacion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MapColeccionOutput {

  public ColeccionDTOOutput toColeccionDTOOutput(Coleccion coleccion) {
    ColeccionDTOOutput coleccionDTOOutput = new ColeccionDTOOutput();
    coleccionDTOOutput.setId(coleccion.getId());
    coleccionDTOOutput.setNombre(coleccion.getDescripcion());
    coleccionDTOOutput.setDescripcion(coleccion.getDescripcion());
    coleccionDTOOutput.setFuentes(coleccion.getFuenteColeccions().stream().map(this::toFuenteDTO).collect(Collectors.toList()));
    coleccionDTOOutput.setCriterios(coleccion.getCriteriosDePertenencia().stream().map(this::toCriterioDTO).toList());
    return coleccionDTOOutput;
  }

  public FuenteColeccion toFuente(FuenteDTO fuente) {
    return new FuenteColeccion(Origen.builder().url(fuente.getUrl()).tipo(fuente.getTipoOrigen()).build());
  }

  public Filtro toCriterio(CriterioDTO criterioDTO) {
    return switch (criterioDTO.getTipo()) {
      case "TITULO" -> new FiltroPorTitulo(criterioDTO.getTitulo());
      case "CATEGORIA" -> new FiltroPorCategoria(new Categoria(criterioDTO.getCategoria()));
      case "DESCRIPCION" -> new FiltroPorDescripcion(criterioDTO.getDescripcion());
      case "FECHAACONTECIMENTO" ->
              new FiltroPorFechaAcontecimiento(criterioDTO.getFechaAcontecimientoInicial(), criterioDTO.getFechaAcontecimientoFinal());
      case "FECHACARGA" ->
              new FiltroPorFechaCarga(criterioDTO.getFechaCargaInicial(), criterioDTO.getFechaCargaFinal());
      case "UBICACION" -> new FiltroUbicacion(criterioDTO.getUbicacion());
      default -> null;
    };
  }

  public CriterioDTO toCriterioDTO(Filtro filtro) {
    return filtro.toCriterioDTO();
  }

  private FuenteDTO toFuenteDTO(FuenteColeccion fuenteColeccion) {
    FuenteDTO fuenteDTO = new FuenteDTO();
    fuenteDTO.setTipoOrigen(fuenteColeccion.getOrigen().getTipo());
    fuenteDTO.setUrl(fuenteColeccion.getOrigen().getUrl());
    return fuenteDTO;
  }
}
