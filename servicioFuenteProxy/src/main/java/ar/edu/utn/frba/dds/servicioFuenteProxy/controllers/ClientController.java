package ar.edu.utn.frba.dds.servicioFuenteProxy.controllers;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.impl.APIService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

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
