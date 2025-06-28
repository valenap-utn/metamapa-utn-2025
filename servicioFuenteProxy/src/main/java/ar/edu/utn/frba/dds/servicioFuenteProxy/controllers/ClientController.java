package ar.edu.utn.frba.dds.servicioFuenteProxy.controllers;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.impl.HechoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ✅ Controller
//
//   Responsabilidad: Punto de entrada de las peticiones HTTP externas (REST API).
//
//   Valida inputs.
//   Orquesta las llamadas a los servicios.
//   Devuelve respuestas HTTP.
//📍 No debería tener lógica de negocio ni llamar directamente al client externo (por ejemplo, hacer requests HTTP a otros servicios).
//🧠 Pensalo como: El que habla con el mundo exterior (cliente REST).

@RestController
public class ClientController {
    private final HechoService hechoService;

    public ClientController(HechoService hechoService) {
        this.hechoService = hechoService;
    }

    @GetMapping
    public List<HechoOutputDTO> getAllHechosExternos(){
        return hechoService.getAllHechosExternos();
    }

    @GetMapping("/{id}")
    public HechoOutputDTO getHechoExternoById(@PathVariable Long id) { return hechoService.getHechoExternoById(id); }
}
