package ar.edu.utn.frba.dds.servicioFuenteEstatica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.ConjuntoHechoEstatica;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoDTOEstatica;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.HechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.services.HechoService;

import java.io.IOException;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/api/hechos")
public class HechoGlobalController {

  private final HechoService hechoService;

  @Autowired
  public HechoGlobalController(HechoService hechoService) {
    this.hechoService = hechoService;
  }

  @PostMapping("/importar")
  public ResponseEntity<String> importar(@RequestParam("archivo") MultipartFile archivo) {
    try {
      Set<Hecho> hechosImportados = hechoService.importarDesdeCSV(archivo);
      return ResponseEntity.ok("Importación exitosa. Se importaron " + hechosImportados.size() + " hechos.");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error al procesar el archivo CSV.");
    }
  }

  @GetMapping
  public ResponseEntity<Set<Hecho>> getAll() {
    return ResponseEntity.ok(hechoService.obtenerTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Hecho> getById(@PathVariable String id) {
    return hechoService.buscarPorID(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/titulo/{titulo}")
  public ResponseEntity<Set<Hecho>> getByTitulo(@PathVariable String titulo) {
    return hechoService.buscarPorTitulo(titulo)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping
  public ResponseEntity<Void> eliminarTodo() {
    hechoService.eliminarTodo();
    return ResponseEntity.noContent().build();
  }
}

