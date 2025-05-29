package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.impl;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.IAPIClient;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class MetaMapaClient implements IAPIClient {
    private final WebClient webClient;

    public MetaMapaClient(WebClient webClient) {
        this.webClient = webClient;
    }
    @Override
    public List<HechoInputDTO> getAllHechosExternos() {
        return webClient.get()
                .uri("hechos") // <-- ruta de la API que devuelve los hechos
                .retrieve() // respuesta
                .bodyToMono(HechoInputDTO.class) // "recibi el Json y mapealo a una instancia de HechoInputDTOResponse"
                .map(HechoInputDTO :: getHechosExternos) // accede al campo que le interesa. No quiere toda la respuesta sino solamente una parte.
                .block();
    }
}

