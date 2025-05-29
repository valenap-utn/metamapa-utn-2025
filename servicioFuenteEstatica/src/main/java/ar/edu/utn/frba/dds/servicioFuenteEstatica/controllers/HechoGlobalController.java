package ar.edu.utn.frba.dds.servicioFuenteEstatica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.HechoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/heechos")
public class HechoGlobalController {
  private final HechoRepository hechosRepository;

  @Autowired
  public HechoGlobalController(HechoRepository hechosRepository) {
    this.hechosRepository = hechosRepository;
  }

  @GetMapping
  public ResponseEntity<Set<Hecho>> getAll() {
    return ResponseEntity.ok(hechosRepository.findAll());
  }

  @GetMapping("/{titulo}")
  public ResponseEntity<Set<Hecho>> getByTitulo(@PathVariable("titulo") String titulo) {
    return hechosRepository.buscarPorTitulo(titulo)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping
  public ResponseEntity<Void> eliminarTodo() {
    hechosRepository.clear();
    return ResponseEntity.noContent().build();
  }
}
