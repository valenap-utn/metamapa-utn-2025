package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.impl;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.Fuente;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.IAPIClient;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.authentication.APIProperties;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input.MetaMapaResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Component
public class MetaMapaClient implements IAPIClient {
    private final WebClient webClient;
    private final APIProperties apiProperties;

    @Override
    public Fuente nombre() {
        return Fuente.META_MAPA;
    }


    public MetaMapaClient(
            WebClient.Builder webClientBuilder, APIProperties apiProperties) {
        this.apiProperties = apiProperties;
        this.webClient = webClientBuilder
                .baseUrl(apiProperties.getBaseUrlMetaMapa())
                .build();
    }

    @Override
    public List<HechoInputDTO> getAllHechosExternos() {
        return webClient
                .get()
                .uri("hechos") // <-- ruta de la API que devuelve los hechos
                .retrieve() // respuesta
                .bodyToMono(MetaMapaResponse.class) // "recibi el Json y mapealo a una instancia de HechoInputDTOResponse"
                .map(MetaMapaResponse :: getHechos) // accede al campo que le interesa. No quiere toda la respuesta sino solamente una parte.
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

