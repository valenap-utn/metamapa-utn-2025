package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoEstadisticasDTO;
import ar.edu.utn.frba.dds.servicioUsuario.servicios.EstadisticaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/estadisticas")
public class ServicioEstadisticasController {
  private final EstadisticaService estadisticaService;


  public ServicioEstadisticasController(EstadisticaService estadisticaService) {
    this.estadisticaService = estadisticaService;
  }

  @GetMapping
  public ConjuntoEstadisticasDTO getEstadisticas() {
    return this.estadisticaService.getEstadisticas();
  }

  @GetMapping("/csv")
  public MultipartFile getEstadisticasEnCSV() {
    return this.estadisticaService.getEstadisticasEnCSV();
  }
}
