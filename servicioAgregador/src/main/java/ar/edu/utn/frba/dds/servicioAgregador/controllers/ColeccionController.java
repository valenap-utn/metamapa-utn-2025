package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.services.IColeccionService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/colecciones")
public class ColeccionController {
  private final IColeccionService coleccionService;

  public ColeccionController(IColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }


  @PostMapping
  public ResponseEntity<ColeccionDTOOutput> crearColeccion(@RequestBody ColeccionDTOInput coleccion) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.coleccionService.crearColeccion(coleccion));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }


  @PutMapping("/{id}")
  public ResponseEntity<ColeccionDTOOutput> cambiarAlgoritmoColeccion(@PathVariable String id, @RequestBody ColeccionDTOInput coleccion) {
    try {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.coleccionService.cambiarAlgoritmo(coleccion, id));
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
                                         @RequestParam(required = false) Float longitud,
                                                         @RequestParam(required = false) boolean curada,
                                          @RequestParam(required = false) boolean entiemporeal) {
    try {
      FiltroDTO filtro = this.toFiltroDTO(categoria, fecha_reporte_desde, fecha_reporte_hasta, fecha_acontecimiento_desde, fecha_acontecimiento_hasta,
              latitud, longitud, curada, entiemporeal);
      return ResponseEntity.ok(this.coleccionService.getHechosPorColeccion(id, filtro)) ;
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  private FiltroDTO toFiltroDTO(String categoria, LocalDate fecha_reporte_desde, LocalDate fecha_reporte_hasta,
                                LocalDate fecha_acontecimiento_desde, LocalDate fecha_acontecimiento_hasta,
                                Float latitud, Float longitud, Boolean curada, Boolean entiemporeal) {
    FiltroDTO filtro = new FiltroDTO();
    filtro.setCategoria(categoria);
    filtro.setFecha_reporte_desde(fecha_reporte_desde);
    filtro.setFecha_reporte_hasta(fecha_reporte_hasta);
    filtro.setFecha_acontecimiento_desde(fecha_acontecimiento_desde);
    filtro.setFecha_acontecimiento_hasta(fecha_acontecimiento_hasta);
    filtro.setLatitud(latitud);
    filtro.setLongitud(longitud);
    filtro.setCurada(curada);
    filtro.setEntiemporeal(entiemporeal);
    return  filtro;
  }

}


