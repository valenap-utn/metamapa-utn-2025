package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.impl;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.Fuente;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.IAPIClient;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.authentication.APIProperties;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.TokenResponse;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input.APIResponse;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input.HechoInputDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

// ✅ Client (IAPIClient y sus implementaciones)
//
//Responsabilidad: Comunicación con sistemas externos (otros servicios, APIs REST, etc).
//
//    Implementa el detalle técnico para consumir una API externa.
//
//    Devuelve DTOs o estructuras intermedias.
//
//📍 No debería tomar decisiones de negocio.
//
//🧠 Pensalo como: El que sabe cómo hablar con otros sistemas.

@Component
public class APIClient implements IAPIClient {
    private final WebClient webClient;
    private final APIProperties apiProperties;

    @Override
    public Fuente nombre() {
        return Fuente.DESASTRES_NATURALES;
    }


    public APIClient(APIProperties apiProperties) {
        this.apiProperties = apiProperties;
        this.webClient = WebClient.builder()
                .baseUrl(apiProperties.getBaseUrl())
                .build();
    }

    public Mono<String> login() {
        Map<String, String> credentials = Map.of(
                "email", apiProperties.getAuth().getEmail(),
                "password", apiProperties.getAuth().getPassword()
        );

        return webClient.post()
                .uri("login")
                .bodyValue(credentials)
                .retrieve()
                .bodyToMono(TokenResponse.class) // <-- convierte el json en un Map<String, Object>
                .map(response -> response.getData().getAccess_token()); // <-- busca el valor asociado a "access_token" y lo castea a un String, porque por defecto es un Object
    }

    @Override
    public List<HechoInputDTO> getAllHechosExternos() {
        String token = login().block();
        return webClient
                .get()
                .uri("desastres") // <-- ruta de la API que devuelve los hechos
                .header("Authorization", "Bearer %s".formatted(token))
                .retrieve() // respuesta
                .bodyToMono(APIResponse.class) // "recibi el Json y mapealo a una instancia de APIResponse"
                .map(APIResponse :: getData)// accede al campo que le interesa. No quiere toda la respuesta sino solamente una parte.
                .block();
    }

    @Override
     public HechoInputDTO getHechoExternoById(Long id){
        String token = login().block();
        return webClient
                .get()
                .uri("desastres/{id}", id) // <-- ruta de la API que devuelve el hecho por id
                .header("Authorization", "Bearer %s".formatted(token))
                .retrieve() // respuesta
                .bodyToMono(HechoInputDTO.class) // "recibi el Json y mapealo a una instancia de HechoInputDTOResponse"
                .block();
    }
}
