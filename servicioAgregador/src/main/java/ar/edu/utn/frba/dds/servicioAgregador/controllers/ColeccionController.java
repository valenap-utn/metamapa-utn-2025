package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.services.IColeccionService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/colecciones")
public class ColeccionController {
  private IColeccionService coleccionService;

  public ColeccionController(IColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }


  @PostMapping
  public ColeccionDTOOutput crearColeccion(@RequestBody ColeccionDTOInput coleccion ) {
    try {
      return this.coleccionService.crearColeccion(coleccion);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @GetMapping
  public List<ColeccionDTOOutput> getColecciones(){
    return this.coleccionService.getAllColecciones();
  }

  @GetMapping("/{id}/hechos")
  public Mono<ConjuntoHechoCompleto> getHechos(@PathVariable String idColeccion) {
    return this.coleccionService.getHechosPorColeccion(idColeccion);
  }
}
