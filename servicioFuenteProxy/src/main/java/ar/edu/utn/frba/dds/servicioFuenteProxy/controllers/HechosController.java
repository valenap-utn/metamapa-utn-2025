package ar.edu.utn.frba.dds.servicioFuenteProxy.controllers;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IHechoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hechos")
public class HechosController {

    private final IHechoService hechoService;

    public HechosController(IHechoService hechoService) { this.hechoService = hechoService; }

    @GetMapping
    public List<HechoOutputDTO> obtenerHechosExternos(){ return this.hechoService.getAllHechosExternos(); }
}

