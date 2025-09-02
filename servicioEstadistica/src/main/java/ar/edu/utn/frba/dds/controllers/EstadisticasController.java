package ar.edu.utn.frba.dds.controllers;

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
import java.util.List;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticasController {

    private final ServicioEstadisticas servicio;

    public EstadisticasController(ServicioEstadisticas servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/{clave}")
    public ResponseEntity<Object> consultar(@PathVariable String clave) {
        Object resultado = servicio.obtenerEstadistica(clave);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{clave}/exportar")
    public ResponseEntity<InputStreamResource> exportar(@PathVariable String clave) throws IOException {
        Path tempFile = Files.createTempFile("estadistica-" + clave, ".csv");
        servicio.exportarCSV(clave, tempFile);

        ByteArrayInputStream stream = new ByteArrayInputStream(Files.readAllBytes(tempFile));
        InputStreamResource resource = new InputStreamResource(stream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + clave + ".csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }

}

