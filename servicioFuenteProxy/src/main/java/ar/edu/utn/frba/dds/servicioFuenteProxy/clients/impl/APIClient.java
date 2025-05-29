package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.impl;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.IAPIClient;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.APIResponse;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;
import org.springframework.beans.factory.annotation.Value;
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
    private final String email;
    private final String password;


    public APIClient(
            WebClient.Builder webClientBuilder,
            @Value("${api.base-url}") String baseUrl,
            @Value("${api.auth.email}") String email,
            @Value("${api.auth.password}") String password
    ){
        this.email = email;
        this.password = password;

        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<String> login() {
        Map<String, String> credentials = Map.of(
                "email", this.email,
                "password", this.password
        );

        return webClient.post()
                .uri("auth/login")
                .bodyValue(credentials)
                .retrieve()
                .bodyToMono(Map.class) // <-- convierte el json en un Map<String, Object>
                .map(response -> (String) response.get("access_token")); // <-- busca el valor asociado a "access_token" y lo castea a un String, porque por defecto es un Object
    }

    @Override
    public List<HechoInputDTO> getAllHechosExternos() {
        String token = login().block();
        return webClient
                .get()
                .uri("desastres") // <-- ruta de la API que devuelve los hechos
                .header("Authorization", "Bearer %s".formatted(token))
                .retrieve() // respuesta
                .bodyToMono(APIResponse.class) // "recibi el Json y mapealo a una instancia de HechoInputDTOResponse"
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
