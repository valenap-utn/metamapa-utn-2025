package ar.edu.utn.frba.dds.servicioEstadistica.controllers;

import ar.edu.utn.frba.dds.servicioEstadistica.model.dtos.ConjuntoEstadisticasDTO;
import ar.edu.utn.frba.dds.servicioEstadistica.services.ServicioEstadisticas;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {

    private final ServicioEstadisticas servicio;

    public EstadisticasController(ServicioEstadisticas servicio) {
        this.servicio = servicio;
    }

    @GetMapping //JSON para el dashboard
    public ConjuntoEstadisticasDTO obtener(@RequestParam("idUsuario") Long idUsuario) {
        return servicio.obtenerEstadisticas(idUsuario);
    }

    @GetMapping("/estadisticas.csv")
    public ResponseEntity<InputStreamResource> exportarCSV(@RequestParam("idUsuario") Long idUsuario) throws IOException {
        ByteArrayInputStream is = servicio.exportarCSV(idUsuario);
        InputStreamResource resource = new InputStreamResource(is);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=estadisticas.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

    /*@GetMapping
    public ResponseEntity<ConjuntoEstadisticasDTO> consultar(@RequestParam Long idUsuario) {
        return ResponseEntity.ok(servicio.obtenerEstadisticas(idUsuario));
    }

    @GetMapping("/estadisticas.csv")
    public ResponseEntity<InputStreamResource> exportar(@RequestParam Long idUsuario) throws IOException {
        Path tempFile = Files.createTempFile("estadisticas", ".csv");
        servicio.exportarCSV(tempFile, idUsuario);

        ByteArrayInputStream stream = new ByteArrayInputStream(Files.readAllBytes(tempFile));
        InputStreamResource resource = new InputStreamResource(stream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=estadisticas.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }*/

    @GetMapping("/recalcular")
    public Boolean recalcular() {
        this.servicio.recalcular();
        return true;
    }
}

