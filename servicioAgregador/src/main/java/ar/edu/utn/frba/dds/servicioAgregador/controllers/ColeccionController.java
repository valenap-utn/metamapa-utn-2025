package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.services.IColeccionService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/colecciones")
public class ColeccionController {
  private final IColeccionService coleccionService;

  public ColeccionController(IColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }


  @PostMapping
  public ResponseEntity<ColeccionDTOOutput> crearColeccion(@RequestBody ColeccionDTOInput coleccion ) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.coleccionService.crearColeccion(coleccion));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<ColeccionDTOOutput>> getColecciones(){
    try {
      return ResponseEntity.ok(this.coleccionService.getAllColecciones());
    } catch(Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/{id}/hechos")
  public ResponseEntity<ConjuntoHechoCompleto> getHechos(@PathVariable String id,
                                         @RequestParam(required = false) String categoria,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "ddmmyyyy") LocalDate fecha_reporte_desde,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "ddmmyyyy") LocalDate fecha_reporte_hasta,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "ddmmyyyy") LocalDate fecha_acontecimiento_desde,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "ddmmyyyy") LocalDate fecha_acontecimiento_hasta,
                                         @RequestParam(required = false) Float latitud,
                                         @RequestParam(required = false) Float longitud) {
    try {
      return ResponseEntity.ok(this.coleccionService.getHechosPorColeccion(id)) ;
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

  }
}
