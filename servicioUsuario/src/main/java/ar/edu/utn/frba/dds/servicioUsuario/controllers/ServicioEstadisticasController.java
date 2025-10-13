package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.servicios.EstadisticaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
public class ServicioEstadisticasController {
  private final EstadisticaService estadisticaService;


  public ServicioEstadisticasController(EstadisticaService estadisticaService) {
    this.estadisticaService = estadisticaService;
  }

  public Object getEstadisticas() {
    return this.estadisticaService.getEstadisticas();
  }

  public Object getEstadisticasEnCSV() {
    return this.estadisticaService.getEstadisticasEnCSV();
  }
}
