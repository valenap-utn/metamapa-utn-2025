package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping('/api/solicitudes')
public class SolicitudController {
  private final ISolicitudService solicitudService;

  public SolicitudController(ISolicitudService solicitudService) {
    this.solicitudService = solicitudService;
  }

  @PostMapping
  public SolicitudOutputDTO crearSolicitud(@RequestBody SolicitudInputDTO solicitudInput) {
    this.solicitudService.crearSolicitud(solicitudInput);
  }


}
