package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.services.IHechoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hechos")
public class HechoController implements IHechoController{
  public IHechoService hechoService;

  public HechoController(IHechoService hechoService) {
    this.hechoService = hechoService;
  }

  @GetMapping
  public ConjuntoHechoCompleto getHechos(){
    return this.hechoService.findAll();
  }
}
