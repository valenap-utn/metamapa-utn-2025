package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTOCompleto;

public interface IHechoService {

  ConjuntoHechoCompleto findAll(FiltroDTO filtro);

  HechoDTOCompleto findHechoById(Long id);
}
