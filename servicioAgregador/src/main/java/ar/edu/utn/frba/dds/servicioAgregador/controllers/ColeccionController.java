package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.services.IColeccionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/colecciones")
public class ColeccionController implements IColeccionController{
  private IColeccionService coleccionService;

  public ColeccionController(IColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }


  @GetMapping
  @Override
  public void crearColeccion(@RequestBody Coleccion coleccion ) {
    this.coleccionService.crearColeccion(coleccion);
  }
}
