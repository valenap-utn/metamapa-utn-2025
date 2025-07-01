package ar.edu.utn.frba.dds.servicioFuenteProxy.clients;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input.HechoInputDTO;

import java.util.List;

public interface IAPIClient {

    List<HechoInputDTO> getAllHechosExternos();

    HechoInputDTO getHechoExternoById(Long id);

    Fuente nombre();

}
