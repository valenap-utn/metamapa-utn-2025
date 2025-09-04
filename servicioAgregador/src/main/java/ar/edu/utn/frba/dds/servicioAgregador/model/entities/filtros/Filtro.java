package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public interface Filtro {
  boolean hechoCumple(Hecho unHecho);

  CriterioDTO toCriterioDTO();
}
