package ar.edu.utn.frba.dds.servicioFuenteProxy.controllers;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.impl.APIService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

// ✅ Controller
//
//Responsabilidad: Punto de entrada de las peticiones HTTP externas (REST API).
//
//    Valida inputs.
//
//    Orquesta las llamadas a los servicios.
//
//    Devuelve respuestas HTTP.
//
//📍 No debería tener lógica de negocio ni llamar directamente al client externo (por ejemplo, hacer requests HTTP a otros servicios).
//
//🧠 Pensalo como: El que habla con el mundo exterior (cliente REST).

@RestController
@RequestMapping("/api/hechos")
public class ClientController {
    private final APIService APIService;

    public ClientController(APIService APIService) {
        this.APIService = APIService;
    }

    @GetMapping
    public List<HechoInputDTO> getAllHechosExternos(){

        return APIService.getAllHechosExternos();
    }

    @GetMapping("/{id}")
    public HechoInputDTO getHechosInputDTOById(@PathVariable Long id) {
        return APIService.getHechoExternoById(id);
    }
}
