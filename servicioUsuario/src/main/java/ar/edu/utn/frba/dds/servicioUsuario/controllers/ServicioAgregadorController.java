package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoCategorias;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoColeccion;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoSolicitudesEliminacionOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.SolicitudEliminacionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.servicios.AgregadorService;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/agregador")
public class ServicioAgregadorController {
  private final AgregadorService agregadorService;

  public ServicioAgregadorController(AgregadorService agregadorService) {
    this.agregadorService = agregadorService;
  }

  @GetMapping("/hechos")
  public ResponseEntity<ConjuntoHechoDTO> findAllHechos(@RequestParam(required = false) String categoria,
                                        @RequestParam(required = false) LocalDateTime fecha_reporte_desde,
                                        @RequestParam(required = false)  LocalDateTime fecha_reporte_hasta,
                                        @RequestParam(required = false) LocalDateTime fecha_acontecimiento_desde,
                                        @RequestParam(required = false) LocalDateTime fecha_acontecimiento_hasta,
                                        @RequestParam(required = false) String provincia,
                                        @RequestParam(required = false) String departamento,
                                        @RequestParam(required = false) String municipio,
                                        @RequestParam(required = false) Integer nroPagina) {
    FiltroDTO filtros = FiltroDTO.builder()
            .categoria(categoria).fecha_reporte_desde(fecha_reporte_desde).fecha_reporte_hasta(fecha_reporte_hasta)
            .fecha_acontecimiento_desde(fecha_acontecimiento_desde).fecha_acontecimiento_hasta(fecha_acontecimiento_hasta)
            .provincia(provincia).municipio(municipio).departamento(departamento).nroPagina(nroPagina)
            .build();
    return ResponseEntity.ok(this.agregadorService.findAllHechos(filtros));
  }

  @GetMapping("/colecciones/{coleccionId}/hechos")
  public ResponseEntity<ConjuntoHechoDTO> findHechosByColeccionId(@PathVariable UUID coleccionId,
                                                   @RequestParam(required = false) String categoria,
                                                   @RequestParam(required = false) LocalDateTime fecha_reporte_desde,
                                                   @RequestParam(required = false)  LocalDateTime fecha_reporte_hasta,
                                                   @RequestParam(required = false) LocalDateTime fecha_acontecimiento_desde,
                                                   @RequestParam(required = false) LocalDateTime fecha_acontecimiento_hasta,
                                                   @RequestParam(required = false) String provincia,
                                                   @RequestParam(required = false) String departamento,
                                                   @RequestParam(required = false) String municipio,
                                                   @RequestParam(required = false) Integer nroPagina) {
    FiltroDTO filtros = FiltroDTO.builder()
            .categoria(categoria).fecha_reporte_desde(fecha_reporte_desde).fecha_reporte_hasta(fecha_reporte_hasta)
            .fecha_acontecimiento_desde(fecha_acontecimiento_desde).fecha_acontecimiento_hasta(fecha_acontecimiento_hasta)
            .provincia(provincia).municipio(municipio).departamento(departamento)
            .nroPagina(nroPagina).build();
    return ResponseEntity.ok(this.agregadorService.findHechosByColeccionId(coleccionId, filtros));
  }

  @GetMapping("/solicitudes")
  public ResponseEntity<ConjuntoSolicitudesEliminacionOutput> findAllSolicitudes() {
    return ResponseEntity.ok(this.agregadorService.findAllSolicitudes());
  }

  @PostMapping("/solicitudes")
  public ResponseEntity<SolicitudEliminacionDTO> crearSolicitud(@RequestBody SolicitudEliminacionDTO solicitudEliminacionDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.agregadorService.crearSolicitud(solicitudEliminacionDTO));
  }

  @PostMapping("/solicitudes/{idSolicitud}/eliminados")
  public ResponseEntity<SolicitudEliminacionDTO> cancelarSolicitud(@PathVariable Long idSolicitud, @RequestBody RevisionDTO revisionDTO) {
    return ResponseEntity.ok( this.agregadorService.cancelarSolicitud(idSolicitud, revisionDTO));
  }

  @PostMapping("/solicitudes/{idSolicitud}/aceptados")
  public ResponseEntity<SolicitudEliminacionDTO> aceptarSolicitud(@PathVariable Long idSolicitud, @RequestBody RevisionDTO revisionDTO) {
    return ResponseEntity.ok(this.agregadorService.aceptarSolicitud(idSolicitud, revisionDTO));
  }

  @PutMapping("/colecciones/{idColeccion}")
  public ResponseEntity<ColeccionDTOOutput> modificarColeccion(@RequestBody  ColeccionDTOInput coleccionDTOInput, @PathVariable UUID idColeccion) {
    return ResponseEntity.ok(this.agregadorService.modificarColeccion(coleccionDTOInput, idColeccion));
  }

  @DeleteMapping("/colecciones/{idColeccion}")
  public ResponseEntity<ColeccionDTOOutput> eliminarColeccion(@PathVariable UUID idColeccion){
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.agregadorService.eliminarColeccion(idColeccion));
  }

  @GetMapping("/colecciones/{idColeccion}")
  public ResponseEntity<ColeccionDTOOutput> findColeccion(@PathVariable UUID idColeccion){
    return ResponseEntity.ok(this.agregadorService.findColeccionById(idColeccion));
  }

  @PostMapping("/colecciones")
  public ResponseEntity<ColeccionDTOOutput> crearColeccion(@RequestBody ColeccionDTOInput coleccion){
    log.info("[ServicioAgregadorController] coleccion a crear:{}", coleccion.getTitulo());
    return ResponseEntity.status(HttpStatus.CREATED).body(this.agregadorService.crearColeccion(coleccion));
  }

  @GetMapping("/hechos/{idHecho}")
  public ResponseEntity<HechoDTOOutput> getHecho(@PathVariable  Long idHecho) {
    return ResponseEntity.ok(this.agregadorService.getHecho(idHecho));
  }

  @GetMapping("/colecciones")
  public ResponseEntity<ConjuntoColeccion> findColecciones() {
    return ResponseEntity.ok(this.agregadorService.findColecciones());
  }

  @GetMapping("/usuarios/{id}/hechos")
  public ResponseEntity<ConjuntoHechoDTO> findHechoByUsuarioId(@PathVariable Long id){
    return ResponseEntity.ok(this.agregadorService.findHechosByIdUsuario(id));
  }

  @GetMapping("/categorias")
  public ResponseEntity<ConjuntoCategorias> findAllCategorias(){
    return ResponseEntity.ok(this.agregadorService.findAllCategorias());
  }

  @GetMapping("/hechos/cantidad")
  public ResponseEntity<Long> getCantidadHechos() {
    return ResponseEntity.ok(this.agregadorService.getCantidadHechos());
  }

  @GetMapping("/fuentes/cantidad")
  public ResponseEntity<Long> getCantidadFuentes() {
    return ResponseEntity.ok(this.agregadorService.getCantidadFuentes());
  }
}
