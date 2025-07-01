package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.services.IHechoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/hechos")
public class HechoController {
  public IHechoService hechoService;

  public HechoController(IHechoService hechoService) {
    this.hechoService = hechoService;
  }

  @GetMapping
  public ResponseEntity<ConjuntoHechoCompleto> getHechos(@RequestParam(required = false) String categoria,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "ddmmyyyy") LocalDate fecha_reporte_desde,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "ddmmyyyy") LocalDate fecha_reporte_hasta,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "ddmmyyyy") LocalDate fecha_acontecimiento_desde,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "ddmmyyyy") LocalDate fecha_acontecimiento_hasta,
                                                         @RequestParam(required = false) Float latitud,
                                                         @RequestParam(required = false) Float longitud,
                                                         @RequestParam(required = false) boolean entiemporeal){
    try {
      FiltroDTO filtro = this.toFiltroDTO(categoria, fecha_reporte_desde, fecha_reporte_hasta, fecha_acontecimiento_desde, fecha_acontecimiento_hasta,
              latitud, longitud, entiemporeal);
      return ResponseEntity.ok(this.hechoService.findAll(filtro));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  private FiltroDTO toFiltroDTO(String categoria, LocalDate fecha_reporte_desde, LocalDate fecha_reporte_hasta,
                                LocalDate fecha_acontecimiento_desde, LocalDate fecha_acontecimiento_hasta,
                                Float latitud, Float longitud, Boolean entiemporeal) {
    FiltroDTO filtro = new FiltroDTO();
    filtro.setCategoria(categoria);
    filtro.setFecha_reporte_desde(fecha_reporte_desde);
    filtro.setFecha_reporte_hasta(fecha_reporte_hasta);
    filtro.setFecha_acontecimiento_desde(fecha_acontecimiento_desde);
    filtro.setFecha_acontecimiento_hasta(fecha_acontecimiento_hasta);
    filtro.setLatitud(latitud);
    filtro.setLongitud(longitud);
    filtro.setEntiemporeal(entiemporeal);
    return  filtro;
  }
}
