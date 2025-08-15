package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.SolicitudError;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ErrorOutputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudInputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudOutputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.services.ISolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {
  private final ISolicitudService solicitudService;

  public SolicitudController(ISolicitudService solicitudService) {
    this.solicitudService = solicitudService;
  }

  @PostMapping
  public ResponseEntity<SolicitudOutputDTO> crearSolicitud(@RequestBody SolicitudInputDTO solicitudInput) {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.solicitudService.crearSolicitud(solicitudInput));
  }

  @PatchMapping
  public ResponseEntity<SolicitudOutputDTO> actualizarSolicitud(@PathVariable Long idSolicitud) {
    return ResponseEntity.ok(this.solicitudService.aceptarSolicitud(idSolicitud));
  }

  @DeleteMapping
  public ResponseEntity<SolicitudOutputDTO> eliminarSolicitud(@PathVariable Long idSolicitud) {
    return ResponseEntity.ok(this.solicitudService.eliminarSolicitud(idSolicitud));
  }


}
