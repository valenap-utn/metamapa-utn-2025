package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.impl;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.IAPIClient;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.APIResponse;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class MetaMapaClient implements IAPIClient {
    private final WebClient webClient;

    public MetaMapaClient(WebClient webClient) {
        this.webClient = WebClient.Builder
                .baseUrl("https://41617578-2031-47e3-b2c6-9908748ca4d1.mock.pstmn.io/api")
                .build();
    }

    @Override
    public List<HechoInputDTO> getAllHechosExternos() {
        return webClient
                .get()
                .uri("hechos") // <-- ruta de la API que devuelve los hechos
                .retrieve() // respuesta
                .bodyToMono(APIResponse.class) // "recibi el Json y mapealo a una instancia de HechoInputDTOResponse"
                .map(APIResponse :: getData) // accede al campo que le interesa. No quiere toda la respuesta sino solamente una parte.
                .block();
    }

    @Override
    public HechoInputDTO getHechoExternoById(Long id){
        return webClient
                .get()
                .uri("hechos/{id}", id) // <-- ruta de la API que devuelve el hecho por id
                .retrieve() // respuesta
                .bodyToMono(HechoInputDTO.class) // "recibi el Json y mapealo a una instancia de HechoInputDTOResponse"
                .block();
    }
}

