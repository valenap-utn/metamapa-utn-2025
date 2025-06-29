package ar.edu.utn.frba.dds.servicioAgregador.services.mappers;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTOCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import java.util.List;

public class MapHechoOutput {
  public ConjuntoHechoCompleto toConjuntoHechoDTOOutput(List<Hecho> hechos) {
    ConjuntoHechoCompleto conjuntoDeHechos = new ConjuntoHechoCompleto();
    List<HechoDTOCompleto> hechosDTO =  hechos.stream().map(this::toHechoDTO).toList();
    conjuntoDeHechos.setHechos(hechosDTO);
    return conjuntoDeHechos;
  }

  public HechoDTOCompleto toHechoDTO(Hecho hecho) {
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
