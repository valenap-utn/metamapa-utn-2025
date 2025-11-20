package ar.edu.utn.frba.dds.servicioAgregador.services.mappers;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTOCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.OrigenDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MapHechoOutput {
  public ConjuntoHechoCompleto toConjuntoHechoDTOOutput(List<Hecho> hechos, List<Categoria> categorias) {
    ConjuntoHechoCompleto conjuntoDeHechos = new ConjuntoHechoCompleto();
    List<HechoDTOCompleto> hechosDTO =  hechos.stream().map(this::toHechoDTO).toList();
    conjuntoDeHechos.setHechos(hechosDTO);
    conjuntoDeHechos.setCategorias(categorias.stream().map(Categoria::getNombre).toList());
    return conjuntoDeHechos;
  }

  public HechoDTOCompleto toHechoDTO(Hecho hecho) {
    HechoDTOCompleto hechoDTO = new HechoDTOCompleto();
    hechoDTO.setId(hecho.getId());
    hechoDTO.setEtiquetas(hecho.getEtiquetas().stream().collect(Collectors.toSet()));
    hechoDTO.setCategoria(hecho.getCategoria());
    hechoDTO.setDescripcion(hecho.getDescripcion());
    hechoDTO.setContenidoMultimedia(hecho.getContenidoMultimedia());
    hechoDTO.setEliminado(hecho.isEliminado());
    hechoDTO.setFechaCarga(hecho.getFechaCarga());
    hechoDTO.setUbicacion(hecho.getUbicacion());
    hechoDTO.setTitulo(hecho.getTitulo());
    hechoDTO.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    hechoDTO.setUsuario(hecho.getUsuarioDTO());
    Origen origen = hecho.getOrigen();
    hechoDTO.setOrigen(new OrigenDTO(origen.getNombreTipo(), origen.getUrl()));
    return hechoDTO;
  }
}
