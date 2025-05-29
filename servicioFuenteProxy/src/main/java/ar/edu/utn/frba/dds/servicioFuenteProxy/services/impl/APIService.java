package ar.edu.utn.frba.dds.servicioFuenteProxy.services.impl;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.IAPIClient;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.impl.APIClient;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

// ✅ Service (IAPIService, APIService)
//
//Responsabilidad: Coordina la lógica de negocio local.
//
//    Orquesta llamadas al client.
//
//    Aplica reglas de negocio (si las hay).
//
//    Transforma datos si es necesario (DTOs → entidades o viceversa).
//
//📍 No debería manejar peticiones HTTP ni saber cómo se implementa el client.
//
//🧠 Pensalo como: El cerebro que decide qué hacer con los datos.

// flujo ideal:

// ClientService hace la llamada HTTP y recibe un JSON con datos de hechos
// El JSON se mapea a una lista intermedia de HechoInputDTOResponse
// HechoService recibe esos HechoInputDTOResponse, los transforma directamente a HechoOutputDTO porque no persiste
// para ser usado localmente y entregados hacia el Servicio Agregador


//CLIENTSERVICE devuelve List<HechoInputDTOResponse>, es decir, los datos sin procesar que vienen de afuera

@Service
public class APIService implements IAPIService {

    private final List<IAPIClient> apiClients;

    public APIService(List<IAPIClient> apiClients){
        this.apiClients = apiClients;
    }

    public List<HechoInputDTO> getAllHechosExternos(){
        return apiClients
                .stream()
                .flatMap(client -> client
                                .getAllHechosExternos()
                                .stream())
                .toList();

    }

    public HechoInputDTO getHechoExternoById(Long id){
        return apiClients
                .stream()
                .flatMap( client -> client
                        .getHechoExternoById(id)
                        .stream());
    }

}

