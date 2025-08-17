package ar.edu.utn.frba.dds.servicioFuenteDinamica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.services.IHechoServicio;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.services.ISolicitudServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HechoSolicitudController {
    private final IHechoServicio hechoServicio;
    private final IHechoRepository hechoRepository;
    private final ISolicitudServicio solicitudServicio;

    public HechoSolicitudController(IHechoServicio hechoServicio, IHechoRepository hechoRepository, ISolicitudServicio solicitudServicio) {
        this.hechoServicio = hechoServicio;
        this.hechoRepository = hechoRepository;
        this.solicitudServicio = solicitudServicio;
    }

    @PostMapping("/hechos")
    public ResponseEntity<Hecho> crearHecho(@RequestBody HechoDTODinamica hecho, @RequestParam("contenidomultimedia") MultipartFile contenidoMultimedia) {
        return ResponseEntity.ok(this.hechoServicio.crearHecho(hecho, contenidoMultimedia));
    }

    @GetMapping("/hechos")
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
    public ResponseEntity<?> modificarHecho(@PathVariable Long id, @RequestBody HechoDTODinamica nuevosDatos) {
        try {
            return ResponseEntity.ok(hechoServicio.modificarHecho(id, nuevosDatos));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/hechos/{id}")
    public ResponseEntity<Hecho> revisarHecho(@PathVariable Long id, @RequestParam String estado, @RequestParam String comentario) {
        return ResponseEntity.ok(hechoServicio.revisarHecho(id, estado, comentario));
    }

    @PutMapping("/hechos/{id}/eliminado")
    public ResponseEntity<?> marcarHechoComoEliminado(@PathVariable Long id) {
        Optional<Hecho> hechoOpt = hechoRepository.findById(id);

        if (hechoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hecho no encontrado");
        }

        Hecho hecho = hechoOpt.get();
        hecho.setEliminado(true);
        hechoRepository.save(hecho);

        return ResponseEntity.ok("Hecho marcado como eliminado");
    }

    @PostMapping("/solicitudes")
    public ResponseEntity<Solicitud> crearSolicitud(@RequestParam Long hechoId, @RequestParam Usuario usuario, @RequestParam String contenido) {
        return ResponseEntity.ok(solicitudServicio.crearSolicitud(hechoId, usuario, contenido));
    }

    @PutMapping("/solicitudes/{id}")
    public ResponseEntity<Solicitud> procesarSolicitud(@PathVariable Long id, @RequestParam String estado, @RequestParam String comentario) {
        return ResponseEntity.ok(solicitudServicio.procesarSolicitud(id, estado, comentario));
    }
}
