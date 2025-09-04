package ar.edu.utn.frba.dds.servicioAgregador.controllers;


import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoSolicitudesOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudInputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudOutputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.services.ISolicitudService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "http://localhost:3000/")
public class SolicitudController {
  private final ISolicitudService solicitudService;

  public SolicitudController(ISolicitudService solicitudService) {
    this.solicitudService = solicitudService;
  }

  @PostMapping
  public ResponseEntity<SolicitudOutputDTO> crearSolicitud(@RequestBody SolicitudInputDTO solicitudInput) {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.solicitudService.crearSolicitud(solicitudInput));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<SolicitudOutputDTO> actualizarSolicitud(@PathVariable Long id) {
    return ResponseEntity.ok(this.solicitudService.aceptarSolicitud(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<SolicitudOutputDTO> eliminarSolicitud(@PathVariable Long id) {
    return ResponseEntity.ok(this.solicitudService.eliminarSolicitud(id));
  }

  @GetMapping
  public ResponseEntity<ConjuntoSolicitudesOutput> buscarSolicitudes() {
    return ResponseEntity.ok(this.solicitudService.buscarSolicitudes());
  }


}
