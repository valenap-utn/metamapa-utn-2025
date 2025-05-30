package ar.edu.utn.frba.dds.servicioFuenteProxy.services;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;

import java.util.List;

public interface IHechoService {
    public List<HechoInputDTO> obtenerHechosExternos();
}
