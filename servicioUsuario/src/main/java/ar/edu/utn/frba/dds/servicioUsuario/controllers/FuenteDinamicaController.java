package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOInput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.SolicitudEdicionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.servicios.FuenteDinamicaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/fuenteDinamica")
public class FuenteDinamicaController {
  private final FuenteDinamicaService fuenteDinamicaService;

  public FuenteDinamicaController(FuenteDinamicaService fuenteDinamicaService) {
    this.fuenteDinamicaService = fuenteDinamicaService;
  }

  @PostMapping(value = "/hechos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public HechoDTOOutput crearHecho(@RequestPart("hecho") HechoDTOInput hecho, @RequestPart(value = "contenidomultimedia", required = false) MultipartFile contenidoMultimedia, @RequestParam String baseUrl){
    return this.fuenteDinamicaService.crearHecho(hecho, contenidoMultimedia, baseUrl);
  }

  @PostMapping("/hechos/{id}/revisados")
  public HechoDTOOutput revisarHecho(@PathVariable Long id, @RequestParam String baseUrl) {
    return this.fuenteDinamicaService.revisarHecho(id, baseUrl);
  }

  @PostMapping("/solicitudes")
  public SolicitudEdicionDTO solicitarModificacion(@RequestBody SolicitudEdicionDTO solicitudEdicion, @RequestParam String baseUrl) {
    return this.fuenteDinamicaService.solicitarModificacion(solicitudEdicion, baseUrl);
  }

  @PutMapping("/solicitudes/{idSolicitud}")
  public SolicitudEdicionDTO  procesarSolicitudEdicion(@PathVariable Long idSolicitud,@RequestParam String baseUrl, @RequestBody RevisionDTO revisionDTO) {
    return this.fuenteDinamicaService.procesarSolicitudEdicion(idSolicitud, baseUrl, revisionDTO);
  }

  @GetMapping("/hechos/pendientes")
  public ConjuntoHechoDTO pendientes(String baseUrl){
    return this.fuenteDinamicaService.findHechosPendientes(baseUrl);
  }

}
