package ar.edu.utn.frba.dds.servicioFuenteDinamica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.ConjuntoHechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.SolicitudDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.services.IHechoServicio;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.services.ISolicitudServicio;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HechoSolicitudController {
    private final IHechoServicio hechoServicio;
    private final ISolicitudServicio solicitudServicio;

    public HechoSolicitudController(IHechoServicio hechoServicio, ISolicitudServicio solicitudServicio) {
        this.hechoServicio = hechoServicio;
        this.solicitudServicio = solicitudServicio;
    }

    @PostMapping(value = "/hechos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HechoDTODinamica> crearHecho(@RequestPart("hecho") HechoDTODinamica hecho, @RequestPart(value = "contenidomultimedia", required = false) MultipartFile contenidoMultimedia) {
        return ResponseEntity.ok(this.toHechoDTO(this.hechoServicio.crearHecho(hecho, contenidoMultimedia)));
    }

    @GetMapping("/hechos")
    public ResponseEntity<ConjuntoHechoDTODinamica> obtenerHechos( @RequestParam(required = false) Boolean pendientes) {
        List<HechoDTODinamica> hechos = hechoServicio.obtenerHechosPublicos(pendientes).stream().map(this::toHechoDTO).toList();
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
        hechoDTO.setUsuario(hecho.getUsuarioDTO());
        return hechoDTO;
    }


    @PostMapping("/hechos/{id}/revisados")
    public ResponseEntity<HechoDTODinamica> revisarHecho(@PathVariable Long id, @RequestBody RevisionDTO revisionDTO) {
        return ResponseEntity.ok(this.toHechoDTO(hechoServicio.revisarHecho(id, revisionDTO)));
    }

    @DeleteMapping("/hechos/{id}")
    public ResponseEntity<HechoDTODinamica> marcarHechoComoEliminado(@PathVariable Long id) {
        Hecho hecho = this.hechoServicio.marcarComoEliminado(id);
        return ResponseEntity.ok(this.toHechoDTO(hecho));
    }

    @PostMapping("/solicitudes")
    public ResponseEntity<SolicitudDTO> crearSolicitud(@RequestBody SolicitudDTO solicitud) {
        return ResponseEntity.ok(this.toSolicitudDTO(solicitudServicio.crearSolicitud(solicitud)));
    }

    @PutMapping("/solicitudes/{id}")
    public ResponseEntity<SolicitudDTO> procesarSolicitud(@PathVariable Long id, @RequestBody RevisionDTO revisionDTO) {
        return ResponseEntity.ok(this.toSolicitudDTO(solicitudServicio.procesarSolicitud(id, revisionDTO)));
    }

    private SolicitudDTO toSolicitudDTO(Solicitud solicitud) {
        SolicitudDTO solicitudDTO = new SolicitudDTO();
        solicitudDTO.setId(solicitud.getId());
        solicitudDTO.setJustificacion(solicitud.getJustificacion());
        solicitudDTO.setUsuario(solicitud.getUsuario().getUsuarioDTO());
        solicitudDTO.setEstado(solicitud.getNombreEstado());
        solicitudDTO.setIdHecho(solicitud.getIdHecho());
        return solicitudDTO;
    }
}
