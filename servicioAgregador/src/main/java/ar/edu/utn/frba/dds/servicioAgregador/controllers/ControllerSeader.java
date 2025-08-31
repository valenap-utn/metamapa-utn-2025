package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.services.ColeccionService;
import ar.edu.utn.frba.dds.servicioAgregador.services.seaders.SeaderColeccion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/init")
public class ControllerSeader {
  private final SeaderColeccion seaderColeccion;
  private final ColeccionService coleccionService;
  public ControllerSeader(SeaderColeccion seaderColeccion, ColeccionService coleccionService) {
    this.seaderColeccion = seaderColeccion;
    this.coleccionService = coleccionService;
  }

  @GetMapping
  public boolean init() {
    this.seaderColeccion.init();
    return true;
  }

  @GetMapping("/actualizar-hechos")
  public boolean actualizarHechos() {
    this.coleccionService.actualizarHechosColecciones().block();
    return true;
  }

  @GetMapping("/consensuar-hechos")
  public boolean consensuarHechos() {
    this.coleccionService.consensuarHechos().block();
    return true;
  }
}
