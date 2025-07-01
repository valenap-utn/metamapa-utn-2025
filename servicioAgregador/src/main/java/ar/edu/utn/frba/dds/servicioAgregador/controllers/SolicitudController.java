package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.SolicitudError;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ErrorOutputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudInputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudOutputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.services.ISolicitudService;
import org.springframework.http.ResponseEntity;
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
    try {
      return ResponseEntity.ok(this.solicitudService.crearSolicitud(solicitudInput));
    } catch (SolicitudError se) {
      ErrorOutputDTO error = new ErrorOutputDTO();
      error.setMensaje(se.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }


}
