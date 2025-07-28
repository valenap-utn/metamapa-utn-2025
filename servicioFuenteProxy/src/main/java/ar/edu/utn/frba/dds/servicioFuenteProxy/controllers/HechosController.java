package ar.edu.utn.frba.dds.servicioFuenteProxy.controllers;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.exceptions.ErrorDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.exceptions.HechoYaEliminado;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IHechoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/hechos")


public class HechosController {

    private final IHechoService hechoService;

    public HechosController(IHechoService hechoService) { this.hechoService = hechoService; }

    @GetMapping
    public ResponseEntity<List<HechoOutputDTO>> obtenerHechos(
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) Double latitud,
        @RequestParam(required = false) Double longitud,
        @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha_reporte_desde,
        @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha_reporte_hasta,
        @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha_acontecimiento_desde,
        @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha_acontecimiento_hasta
        ) {
        List<HechoOutputDTO> hechoOutputDTOS = this.hechoService.getHechosExternos(
                categoria, latitud, longitud, fecha_reporte_desde, fecha_reporte_hasta, fecha_acontecimiento_desde, fecha_acontecimiento_hasta
        );
        return ResponseEntity.ok().body(hechoOutputDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> eliminarHecho(@PathVariable Long id, @RequestParam String clientNombre) {
        hechoService.marcarComoEliminado(id, clientNombre);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    public ResponseEntity<ErrorDTO> exceptionHandler(UnsupportedOperationException error ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(error.getMessage()));
    }

    @ExceptionHandler(value = HechoYaEliminado.class)
    public ResponseEntity<ErrorDTO> handlerHechoYaEliminado(HechoYaEliminado error){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(error.getMessage()));
    }
}

