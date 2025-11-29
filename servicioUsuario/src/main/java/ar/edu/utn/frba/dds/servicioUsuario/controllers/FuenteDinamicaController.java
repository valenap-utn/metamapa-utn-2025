package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoCategorias;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoSolicitudesEdicionOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOInput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.SolicitudEdicionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.servicios.FuenteDinamicaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@Slf4j
public class FuenteDinamicaController {
  private final FuenteDinamicaService fuenteDinamicaService;

  public FuenteDinamicaController(FuenteDinamicaService fuenteDinamicaService) {
    this.fuenteDinamicaService = fuenteDinamicaService;
  }

  @PostMapping(value = "/hechos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<HechoDTOOutput> crearHecho(@RequestPart("hecho") HechoDTOInput hecho, @RequestPart(value = "contenidomultimedia", required = false) MultipartFile contenidoMultimedia, @RequestParam String baseUrl){
    return ResponseEntity.status(HttpStatus.CREATED).body(this.fuenteDinamicaService.crearHecho(hecho, contenidoMultimedia, baseUrl));
  }

  @PostMapping("/hechos/{id}/revisados")
  public ResponseEntity<HechoDTOOutput> revisarHecho(@PathVariable Long id, @RequestParam String baseUrl, @RequestBody RevisionDTO revisionDTO) {
    return ResponseEntity.ok(this.fuenteDinamicaService.revisarHecho(id, baseUrl, revisionDTO));
  }

  @PostMapping(value = "/solicitudes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<SolicitudEdicionDTO> solicitarModificacion(@RequestPart("solicitud") SolicitudEdicionDTO solicitudEdicion, @RequestPart(value = "contenidomultimedia", required = false) MultipartFile contenidoMultimedia, @RequestParam String baseUrl) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.fuenteDinamicaService.solicitarModificacion(solicitudEdicion, contenidoMultimedia, baseUrl));
  }

  @PutMapping("/solicitudes/{idSolicitud}")
  public ResponseEntity<SolicitudEdicionDTO>  procesarSolicitudEdicion(@PathVariable Long idSolicitud,@RequestParam String baseUrl, @RequestBody RevisionDTO revisionDTO) {
    return ResponseEntity.ok(this.fuenteDinamicaService.procesarSolicitudEdicion(idSolicitud, baseUrl, revisionDTO));
  }

  @GetMapping("/hechos/pendientes")
  public ResponseEntity<ConjuntoHechoDTO> hechosPendientes(@RequestParam String baseUrl){
    return ResponseEntity.ok(this.fuenteDinamicaService.findHechosPendientes(baseUrl));
  }

  @GetMapping("/solicitudes")
  public ResponseEntity<ConjuntoSolicitudesEdicionOutput> findAllSolicitudes(@RequestParam String baseUrl){
    return ResponseEntity.ok(this.fuenteDinamicaService.findAllSolicitudes(baseUrl));
  }

  @GetMapping("/usuarios/{id}/hechos")
  public ResponseEntity<ConjuntoHechoDTO> hechosUsuario(@PathVariable Long id, @RequestParam String baseUrl){
    return ResponseEntity.ok(this.fuenteDinamicaService.findHechosPorUsuario(id, baseUrl));
  }

  @GetMapping("/hechos/{id}")
  public ResponseEntity<HechoDTOOutput> hechoById(@PathVariable Long id, @RequestParam String baseUrl){
    return ResponseEntity.ok(this.fuenteDinamicaService.findHechoById(id, baseUrl));
  }

  @GetMapping("/categorias")
  public ResponseEntity<ConjuntoCategorias> findAllCategorias(@RequestParam String baseUrl){
    return ResponseEntity.ok(this.fuenteDinamicaService.findAllCategorias(baseUrl));
  }

  //Paara nuevos hechos
  @GetMapping("/nuevos-hechos")
  public ResponseEntity<ConjuntoHechoDTO> findNuevosHechos(@RequestParam String baseUrl, @RequestParam(required = false,defaultValue = "TODAS")String estado, @RequestParam(required = false, defaultValue = "0") Integer nroPagina){
    log.info("Pidiendo los nuevos hechos  fuente dinamica...");
    return ResponseEntity.ok(this.fuenteDinamicaService.findNuevosHechos(baseUrl, estado, nroPagina));
  }

}
