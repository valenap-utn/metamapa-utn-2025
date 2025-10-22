package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.dtos.ConjuntoEstadisticasDTO;
import ar.edu.utn.frba.dds.services.ServicioEstadisticas;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {

    private final ServicioEstadisticas servicio;

    public EstadisticasController(ServicioEstadisticas servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<ConjuntoEstadisticasDTO> consultar() {
        return ResponseEntity.ok(servicio.obtenerEstadisticas());
    }

    @GetMapping("/estadisticas.csv")
    public ResponseEntity<InputStreamResource> exportar() throws IOException {
        Path tempFile = Files.createTempFile("estadisticas", ".csv");
        servicio.exportarCSV(tempFile);

        ByteArrayInputStream stream = new ByteArrayInputStream(Files.readAllBytes(tempFile));
        InputStreamResource resource = new InputStreamResource(stream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=estadisticas.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

}

