package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.services.seaders.SeaderColeccion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/init")
public class ControllerSeader {
  private final SeaderColeccion seaderColeccion;
  public ControllerSeader(SeaderColeccion seaderColeccion) {
    this.seaderColeccion = seaderColeccion;
  }

  @GetMapping
  public boolean init() {
    this.seaderColeccion.init();
    return true;
  }
}
