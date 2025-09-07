package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public abstract class Filtro {

  public abstract boolean hechoCumple(Hecho unHecho);

  public abstract CriterioDTO toCriterioDTO();
}
