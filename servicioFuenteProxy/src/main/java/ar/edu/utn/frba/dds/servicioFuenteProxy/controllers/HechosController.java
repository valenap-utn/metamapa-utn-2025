package ar.edu.utn.frba.dds.servicioFuenteProxy.controllers;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IHechoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/hechos")


public class HechosController {

    private final IHechoService hechoService;

    public HechosController(IHechoService hechoService) { this.hechoService = hechoService; }

    @GetMapping
    public List<HechoOutputDTO> obtenerHechos(
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) Double latitud,
        @RequestParam(required = false) Double longitud,
        @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha_reporte_desde,
        @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha_reporte_hasta,
        @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha_acontecimiento_desde,
        @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha_acontecimiento_hasta
        ) {
        return this.hechoService.getHechosExternos(
                categoria, latitud, longitud, fecha_reporte_desde, fecha_reporte_hasta, fecha_acontecimiento_desde, fecha_acontecimiento_hasta
        );
    }

    @PutMapping("/{id}")
    public void eliminarHecho(@PathVariable Long id, @RequestParam String clientNombre) {
        if(clientNombre.equals("DESASTRES_NATURALES")){
            hechoService.marcarComoEliminado(id);
        } else throw new UnsupportedOperationException("El cliente no admite solicitudes de eliminaciòn");
    }
}

