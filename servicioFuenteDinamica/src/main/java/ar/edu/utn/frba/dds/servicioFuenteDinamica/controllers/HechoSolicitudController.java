package ar.edu.utn.frba.dds.servicioFuenteDinamica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.ConjuntoCategorias;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.ConjuntoHechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.ConjuntoSolicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.FiltroDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.SolicitudDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Categoria;
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
    public ResponseEntity<ConjuntoHechoDTODinamica> obtenerHechos( @RequestParam(required = false) Boolean pendientes, @RequestParam(required = false) Long idUsuario, @RequestParam(required = false) Boolean servicioAgregador, @RequestParam(required = false) Integer nroPagina) {
        FiltroDTODinamica filtroDTODinamica = FiltroDTODinamica.builder()
                .nroPagina(nroPagina).servicioAgregador(servicioAgregador).idUsuario(idUsuario).pendientes(pendientes).build();
        List<HechoDTODinamica> hechos = hechoServicio.obtenerHechosPublicos(filtroDTODinamica).stream().map(this::toHechoDTO).toList();
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
        hechoDTO.setEstado(hecho.getEstado().name());
        hechoDTO.setFechaAprobacion(hecho.getFechaAprobacion());
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

    @PostMapping(value = "/solicitudes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SolicitudDTO> crearSolicitud(@RequestPart("solicitud") SolicitudDTO solicitud, @RequestPart(value ="contenidomultimedia", required = false) MultipartFile contenidoMultimedia) {
        return ResponseEntity.ok(this.toSolicitudDTO(solicitudServicio.crearSolicitud(solicitud, contenidoMultimedia)));
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

    @GetMapping("/solicitudes")
    public ResponseEntity<ConjuntoSolicitud> obtenerSolicituds() {
        List<Solicitud> solicituds = this.solicitudServicio.findAllSolicitudes();
        ConjuntoSolicitud conjuntoSolicitud = new ConjuntoSolicitud();
        conjuntoSolicitud.setSolicitudes(solicituds.stream().map(this::toSolicitudDTO).toList());
        return ResponseEntity.ok(conjuntoSolicitud);
    }

    @GetMapping("/hechos/{idHecho}")
    public ResponseEntity<HechoDTODinamica> findHechoById(@PathVariable Long idHecho) {
        return ResponseEntity.ok(this.toHechoDTO(this.hechoServicio.findHechoById(idHecho)));
    }

    @GetMapping("/categorias")
    public ResponseEntity<ConjuntoCategorias> obtenerCategorias() {
        List<Categoria> categorias = this.hechoServicio.findAllCategorias();
        ConjuntoCategorias conjuntoCategorias = new ConjuntoCategorias();
        conjuntoCategorias.setCategorias(categorias.stream().map(Categoria::getNombre).toList());
        return ResponseEntity.ok(conjuntoCategorias);
    }

    //Para nuevos hechos
    @GetMapping("/nuevos-hechos")
    public ResponseEntity<ConjuntoHechoDTODinamica> obtenerHechosNuevos(@RequestParam(required = false,defaultValue = "TODAS")String estado, @RequestParam(required = false) Integer nroPagina) {
        List<Hecho> hechos = this.hechoServicio.obtenerHechosNuevos(estado, nroPagina);
        ConjuntoHechoDTODinamica conjunto = new ConjuntoHechoDTODinamica();
        conjunto.setHechos(hechos.stream().map(this::toHechoDTO).toList());
        return ResponseEntity.ok(conjunto);
    }
}
