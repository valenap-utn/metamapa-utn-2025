package ar.edu.utn.frba.dds.servicioFuenteDinamica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.HechoNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.ConjuntoHechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTOModificacionDinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.services.IHechoServicio;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.services.ISolicitudServicio;
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
    public ResponseEntity<HechoDTODinamica> crearHecho(@RequestPart("hecho") HechoDTODinamica hecho, @RequestPart(value = "contenidomultimedia", required = false) MultipartFile contenidoMultimedia) {
        return ResponseEntity.ok(this.toHechoDTO(this.hechoServicio.crearHecho(hecho, contenidoMultimedia)));
    }

    @GetMapping("/hechos")
    public ResponseEntity<ConjuntoHechoDTODinamica> obtenerHechosPublicos() {
        List<HechoDTODinamica> hechos = hechoServicio.obtenerHechosPublicos().stream().map(this::toHechoDTO).toList();
        ConjuntoHechoDTODinamica conjuntoHechos = new ConjuntoHechoDTODinamica();
        conjuntoHechos.setHechos(hechos);
        return ResponseEntity.ok(conjuntoHechos);
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
        hechoDTO.setIdUsuario(hecho.getIdUsuario());
        return hechoDTO;
    }

    @PutMapping("/hechos/{id}")
    public ResponseEntity<?> modificarHecho(@PathVariable Long id, @RequestBody HechoDTOModificacionDinamica nuevosDatos) {
            return ResponseEntity.ok(hechoServicio.modificarHecho(id, nuevosDatos));
    }

    @PostMapping("/hechos/{id}/revisados")
    public ResponseEntity<Hecho> revisarHecho(@PathVariable Long id, @RequestBody RevisionDTO revisionDTO) {
        return ResponseEntity.ok(hechoServicio.revisarHecho(id, revisionDTO.getEstado(), revisionDTO.getComentario()));
    }

    @PatchMapping("/hechos/{id}")
    public ResponseEntity<?> marcarHechoComoEliminado(@PathVariable Long id) {
        Optional<Hecho> hechoOpt = hechoRepository.findById(id);

        if (hechoOpt.isEmpty()) {
            throw new HechoNoEncontrado("Hecho de id: "+ id +" no encontrado");
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
