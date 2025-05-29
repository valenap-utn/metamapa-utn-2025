package ar.edu.utn.frba.dds.servicioFuenteProxy.services.impl;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.IAPIClient;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IAPIService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

// flujo ideal:

// ClientService hace la llamada HTTP y recibe un JSON con datos de hechos
// El JSON se mapea a una lista intermedia de HechoInputDTOResponse
// HechoService recibe esos HechoInputDTOResponse, los transforma directamente a HechoOutputDTO porque no persiste
// para ser usado localmente y entregados hacia el Servicio Agregador


//CLIENTSERVICE devuelve List<HechoInputDTOResponse>, es decir, los datos sin procesar que vienen de afuera

@Service
public class APIService implements IAPIService {

    public final IAPIClient apiClient;

    public List<HechoInputDTO> getAllHechosExternos(){
        apiClient.getAllHechosExternos();

    }

}
