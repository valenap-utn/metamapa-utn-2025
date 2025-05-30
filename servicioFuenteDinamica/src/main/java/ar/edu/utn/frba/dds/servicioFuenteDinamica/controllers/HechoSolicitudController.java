package ar.edu.utn.frba.dds.servicioFuenteDinamica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.services.HechoServicio;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.services.SolicitudServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HechoSolicitudController {

    @Autowired
    private HechoServicio hechoServicio;

    @Autowired
    private SolicitudServicio solicitudServicio;

    @PostMapping
    public ResponseEntity<Hecho> crearHecho(@RequestBody Hecho hecho, @RequestParam Optional<Long> usuarioId) {
        return ResponseEntity.ok(hechoServicio.crearHecho(hecho, usuarioId));
    }

    @GetMapping
    public List<HechoDTODinamica> obtenerHechosPublicos() {
        return hechoServicio.obtenerHechosPublicos().stream().map(this::toHechoDTO).toList();
    }

    public HechoDTODinamica toHechoDTO(Hecho hecho) {
        HechoDTODinamica hechoDTO = new HechoDTODinamica();
        hechoDTO.setId(hecho.getId());
        hechoDTO.setCategoria(hecho.getCategoria());
        hechoDTO.setDescripcion(hecho.getDescripcion());
        hechoDTO.setFechaCarga(hecho.getFechaCarga());
        hechoDTO.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        hechoDTO.setContenidoMultimedia(hecho.getContenidoMultimedia());
        hechoDTO.setTitulo(hecho.getTitulo());
        hechoDTO.setUbicacion(hecho.getUbicacion());
        return hechoDTO;
    }


    @PutMapping("/hechos/{id}")
    public ResponseEntity<?> modificarHecho(@PathVariable Long id, @RequestBody Hecho nuevosDatos) {
        try {
            return ResponseEntity.ok(hechoServicio.modificarHecho(id, nuevosDatos));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/hechos/{id}")
    public ResponseEntity<Hecho> revisarHecho(@PathVariable Long id, @RequestParam String estado, @RequestParam Optional<String> comentario) {
        return ResponseEntity.ok(hechoServicio.revisarHecho(id, estado, comentario));
    }


    @PostMapping("/solicitudes")
    public ResponseEntity<Solicitud> crearSolicitud(@RequestParam Long hechoId, @RequestParam Usuario usuario, @RequestParam String contenido) {
        return ResponseEntity.ok(solicitudServicio.crearSolicitud(hechoId, usuario, contenido));
    }

    @PostMapping("/solicitudes/{id}")
    public ResponseEntity<Solicitud> procesarSolicitud(@PathVariable Long id, @RequestParam String estado, @RequestParam Optional<String> comentario) {
        return ResponseEntity.ok(solicitudServicio.procesarSolicitud(id, estado, comentario));
    }
}
