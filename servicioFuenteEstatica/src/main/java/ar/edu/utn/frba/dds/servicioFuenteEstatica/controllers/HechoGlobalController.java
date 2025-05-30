package ar.edu.utn.frba.dds.servicioFuenteEstatica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.ConjuntoHechoEstatica;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoDTOEstatica;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.HechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.IHechoRepository;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/hechos")
public class HechoGlobalController {
  private final IHechoRepository hechosRepository;

  @Autowired
  public HechoGlobalController(IHechoRepository hechosRepository) {
    this.hechosRepository = hechosRepository;
  }

  @GetMapping
  public Set<HechoDTOEstatica> getAll() {

    return hechosRepository.findAll().stream().map(this::toHechoDTOEstatica).collect(Collectors.toSet());
  }

  @GetMapping("/{titulo}")
  public Set<HechoDTOEstatica> getByTitulo(@PathVariable("titulo") String titulo) {
    return hechosRepository.buscarPorTitulo(titulo)
        .map(this::toSetHechoDTOEstatica)
        .orElse(new HashSet<>());
  }

  @DeleteMapping
  public ResponseEntity<Void> eliminarTodo() {
    hechosRepository.clear();
    return ResponseEntity.noContent().build();
  }

  public Set<HechoDTOEstatica> toSetHechoDTOEstatica(Set<Hecho> hechos) {
    return hechos.stream().map(this::toHechoDTOEstatica).collect(Collectors.toSet());
  }

  public HechoDTOEstatica toHechoDTOEstatica(Hecho hecho) {
    HechoDTOEstatica dto = new HechoDTOEstatica();
//    dto.setId(hecho.getColeccion().getId().node());
    dto.setTitulo(hecho.getTitulo());
    dto.setUbicacion(hecho.getUbicacion());
    dto.setDescripcion(hecho.getDescripcion());
    dto.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    dto.setFechaCarga(hecho.getFechaCarga());
    dto.setCategoria(hecho.getCategoria());
    return dto;
  }
}
