package ar.edu.utn.frba.dds.servicioFuenteProxy.services;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoOutputDTO;

import java.util.List;

public interface IHechoService {
    List<HechoOutputDTO> getAllHechosExternos(); // <-- se encarga de hacer la llamada al endpoint de login y devolver un Mono con el token

}
