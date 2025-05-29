package ar.edu.utn.frba.dds.servicioFuenteProxy.services;

import ar.edu.utn.frba.dds.servicioFuenteProxy.models.dtos.HechoOutputDTO;

import java.util.List;

public interface IHechoService {
    public List<HechoOutputDTO> transformarHechos();
}
