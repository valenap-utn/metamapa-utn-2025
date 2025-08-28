package ar.edu.utn.frba.dds.servicioFuenteEstatica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.services.SeaderUsuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/init")
public class ControllerSeader {
  private final SeaderUsuario seaderColeccion;
  public ControllerSeader(SeaderUsuario seaderColeccion) {
    this.seaderColeccion = seaderColeccion;
  }

  @GetMapping
  public boolean init() {
    this.seaderColeccion.init();
    return true;
  }
}
