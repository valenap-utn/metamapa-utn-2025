package ar.edu.utn.frba.dds.servicioFuenteProxy.clients;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IAPIClient {
    List<HechoInputDTO> getAllHechosExternos();
}
