package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoColeccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.services.IColeccionService;

import java.time.LocalDateTime;
import java.util.List;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/colecciones")
@CrossOrigin(origins = "http://localhost:3000/")
public class ColeccionController {
  private final IColeccionService coleccionService;
  public ColeccionController(IColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }


  @GetMapping
  public ResponseEntity<ConjuntoColeccion> getColecciones(){
    List<ColeccionDTOOutput> colecciones = this.coleccionService.getAllColecciones();
    ConjuntoColeccion conjuntoColeccion = new ConjuntoColeccion();
    conjuntoColeccion.setColecciones(colecciones);
      return ResponseEntity.ok(conjuntoColeccion);
  }

  @GetMapping("/{id}/hechos")
  public ResponseEntity<ConjuntoHechoCompleto> getHechos(@PathVariable UUID id,
                                         @RequestParam(required = false) Long idUsuario,
                                                         @RequestParam(required = false) String categoria,
                                         @RequestParam(required = false) LocalDateTime fecha_reporte_desde,
                                         @RequestParam(required = false) LocalDateTime fecha_reporte_hasta,
                                         @RequestParam(required = false) LocalDateTime fecha_acontecimiento_desde,
                                         @RequestParam(required = false) LocalDateTime fecha_acontecimiento_hasta,
                                         @RequestParam(required = false) Float latitud,
                                         @RequestParam(required = false) Float longitud,
                                                         @RequestParam(required = false) boolean curada,
                                          @RequestParam(required = false) boolean entiemporeal,
                                                         @RequestParam(required = false) String provincia,
                                                         @RequestParam(required = false) String departamento,
                                                         @RequestParam(required = false) String municipio) {
      FiltroDTO filtro = FiltroDTO.builder()
              .fecha_acontecimiento_desde(fecha_acontecimiento_desde)
              .fecha_acontecimiento_hasta(fecha_acontecimiento_hasta)
              .fecha_reporte_desde(fecha_reporte_desde)
              .fecha_reporte_hasta(fecha_reporte_hasta)
              .categoria(categoria)
              .latitud(latitud)
              .longitud(longitud)
              .curada(curada)
              .entiemporeal(entiemporeal)
              .idUsuario(idUsuario)
              .departamento(departamento)
              .municipio(municipio)
              .provincia(provincia)
              .build();

      return ResponseEntity.ok(this.coleccionService.getHechosPorColeccion(id, filtro)) ;
  }

  @GetMapping("/{id}")
  public ColeccionDTOOutput getColeccionById(@PathVariable UUID id){
    return this.coleccionService.findColeccionById(id);
  }

}


