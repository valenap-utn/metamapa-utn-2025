package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOInput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.SolicitudEdicionDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fuenteDinamica")
public class FuenteDinamicaController {

  @PostMapping("/hechos")
  public HechoDTOOutput crearHecho(HechoDTOInput hecho, String baseUrl){

  }

  @PutMapping("/hechos/{id}")
  public HechoDTOOutput actualizarHecho(@PathVariable Long id, HechoDTOInput hecho, String baseUrl) {

  }

  @PostMapping("/hechos/{id}/revisados")
  public HechoDTOOutput revisarHecho(Long idHecho, String baseUrl) {

  }

  @PostMapping("/solicitudes")
  public SolicitudEdicionDTO solicitarModificacion(SolicitudEdicionDTO solicitudEdicion, String baseUrl) {

  }

  @PutMapping("/solicitudes/{idSolicitud}")
  public SolicitudEdicionDTO  procesarSolicitudEdicion(@PathVariable Long idSolicitud, String baseUrl, RevisionDTO revisionDTO) {
  }
}
