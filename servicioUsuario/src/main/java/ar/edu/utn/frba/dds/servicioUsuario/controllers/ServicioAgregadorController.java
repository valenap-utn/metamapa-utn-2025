package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoCategorias;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoColeccion;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoHechoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ConjuntoSolicitudesEliminacionOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.SolicitudEliminacionDTO;
import ar.edu.utn.frba.dds.servicioUsuario.servicios.AgregadorService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agregador")
public class ServicioAgregadorController {
  private final AgregadorService agregadorService;

  public ServicioAgregadorController(AgregadorService agregadorService) {
    this.agregadorService = agregadorService;
  }

  @GetMapping("/hechos")
  public ConjuntoHechoDTO findAllHechos(@RequestParam(required = false) String categoria,
                                        @RequestParam(required = false) LocalDateTime fecha_reporte_desde,
                                        @RequestParam(required = false)  LocalDateTime fecha_reporte_hasta,
                                        @RequestParam(required = false) LocalDateTime fecha_acontecimiento_desde,
                                        @RequestParam(required = false) LocalDateTime fecha_acontecimiento_hasta,
                                        @RequestParam(required = false) Float latitud,
                                        @RequestParam(required = false) Float longitud) {
    FiltroDTO filtros = FiltroDTO.builder()
            .categoria(categoria).fecha_reporte_desde(fecha_reporte_desde).fecha_reporte_hasta(fecha_reporte_hasta)
            .fecha_acontecimiento_desde(fecha_acontecimiento_desde).fecha_acontecimiento_hasta(fecha_acontecimiento_hasta).build();
    return this.agregadorService.findAllHechos(filtros);
  }

  @GetMapping("/colecciones/{coleccionId}/hechos")
  public  ConjuntoHechoDTO findHechosByColeccionId(@PathVariable UUID coleccionId, FiltroDTO filtro) {
    return this.agregadorService.findHechosByColeccionId(coleccionId, filtro);
  }

  @GetMapping("/solicitudes")
  public ConjuntoSolicitudesEliminacionOutput findAllSolicitudes() {
    return this.agregadorService.findAllSolicitudes();
  }

  @PostMapping("/solicitudes")
  public SolicitudEliminacionDTO crearSolicitud(SolicitudEliminacionDTO solicitudEliminacionDTO) {
    return this.agregadorService.crearSolicitud(solicitudEliminacionDTO);
  }

  @DeleteMapping("/solicitudes/{idSolicitud}")
  public SolicitudEliminacionDTO cancelarSolicitud(@PathVariable Long idSolicitud) {
    return this.agregadorService.cancelarSolicitud(idSolicitud);
  }

  @PutMapping("/solicitudes/{idSolicitud}")
  public SolicitudEliminacionDTO aceptarSolicitud(@PathVariable Long idSolicitud) {
    return this.agregadorService.aceptarSolicitud(idSolicitud);
  }

  @PutMapping("/colecciones/{idColeccion}")
  public ColeccionDTOOutput modificarColeccion(ColeccionDTOInput coleccionDTOInput, @PathVariable UUID idColeccion) {
    return this.agregadorService.modificarColeccion(coleccionDTOInput, idColeccion);
  }

  @DeleteMapping("/colecciones/{idColeccion}")
  public ColeccionDTOOutput eliminarColeccion(@PathVariable UUID idColeccion){
    return this.agregadorService.eliminarColeccion(idColeccion);
  }

  @PostMapping("/colecciones")
  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccion){
    return this.agregadorService.crearColeccion(coleccion);
  }

  @GetMapping("/hechos/{idHecho}")
  public HechoDTOOutput getHecho(@PathVariable  Long idHecho) {
    return this.agregadorService.getHecho(idHecho);
  }

  @GetMapping("/colecciones")
  public ConjuntoColeccion findColecciones() {
    return this.agregadorService.findColecciones();
  }

  @GetMapping("/usuarios/{id}/hechos")
  public ConjuntoHechoDTO findHechoByUsuarioId(@PathVariable Long id){
    return this.agregadorService.findHechosByIdUsuario(id);
  }

  @GetMapping("/api/categorias")
  public ConjuntoCategorias findAllCategorias(){
    return this.agregadorService.findAllCategorias();
  }
}
