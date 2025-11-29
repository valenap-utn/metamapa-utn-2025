package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoEstadisticasDTO;
import ar.edu.utn.frba.dds.servicioUsuario.servicios.EstadisticaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
public class ServicioEstadisticasController {
  private final EstadisticaService estadisticaService;


  public ServicioEstadisticasController(EstadisticaService estadisticaService) {
    this.estadisticaService = estadisticaService;
  }

  @GetMapping
  public ResponseEntity<ConjuntoEstadisticasDTO> getEstadisticas() {
    return ResponseEntity.ok(this.estadisticaService.getEstadisticas());
  }

  @GetMapping("/csv")
  public ResponseEntity<byte[]> getEstadisticasEnCSV() {
    byte[] csv = this.estadisticaService.getEstadisticasEnCSV();
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=estadisticas.csv")
        .contentType(MediaType.parseMediaType("text/csv"))
        .body(csv);
  }
}
