package ar.edu.utn.frba.dds.servicioAgregador.services.mappers;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTOCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.util.Set;
import java.util.stream.Collectors;

public class MapHechoOutput {
  public ConjuntoHechoCompleto toConjuntoHechoDTOOutput(Set<Hecho> hechos) {
    ConjuntoHechoCompleto conjuntoDeHechos = new ConjuntoHechoCompleto();
    Set<HechoDTO<HechoDTOCompleto>> hechosDTO =  hechos.stream().map(this::toHechoDTO).collect(Collectors.toSet());
    conjuntoDeHechos.setHechos(hechosDTO);
    return conjuntoDeHechos;
  }

  private HechoDTO<HechoDTOCompleto> toHechoDTO(Hecho hecho) {
    HechoDTOCompleto hechoDTO = new HechoDTOCompleto();
    hechoDTO.setId(hecho.getId());
    hechoDTO.setEtiquetas(hecho.getEtiquetas());
    hechoDTO.setCategoria(hecho.getCategoria());
    hechoDTO.setDescripcion(hecho.getDescripcion());
    hechoDTO.setContenidoMultimedia(hecho.getContenidoMultimedia());
    hechoDTO.setEliminado(hecho.isEliminado());
    hechoDTO.setFechaCarga(hecho.getFechaCarga());
    hechoDTO.setUbicacion(hecho.getUbicacion());
    hechoDTO.setTitulo(hecho.getTitulo());
    hechoDTO.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    return hechoDTO;
  }
}
