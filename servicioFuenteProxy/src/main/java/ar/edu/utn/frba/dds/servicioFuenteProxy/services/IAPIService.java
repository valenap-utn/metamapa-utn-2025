package ar.edu.utn.frba.dds.servicioFuenteProxy.services;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;
import reactor.core.publisher.Mono;

import java.util.List;

// Client es el que se conecta a la fuente externa (API) para traer datos, y devuelve List<HechoInputDTO>, es decir, los datos "sin procesar"
public interface IAPIService {
    List<HechoInputDTO> getAllHechosExternos(); // <-- se encarga de hacer la llamada al endpoint de login y devolver un Mono con el token
    HechoInputDTO getHechoExternoById(Long id);
}
