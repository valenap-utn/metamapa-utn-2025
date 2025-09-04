package ar.edu.utn.frba.dds.servicioFuenteEstatica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.ConjuntoHechoDTOEstatica;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoDTOEstatica;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.services.IHechoService;
import org.springframework.beans.factory.annotation.Autowired;
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

  /* Manejo con HechoService, dado que el Controller no debe conocer directamente la persistencia,
    sino la lógica de negocio que usa.
    Si el Controller dependiera del Repository, tendría que meter lógica del Service acá y romperia
    con el principio de responsabilidad única.
    => Si mañana cambiara de un repo en memoria a una base de datos real
                                                                   => mi controller ni se entera */
  private final IHechoService hechoService;

  @Autowired
  public HechoGlobalController(IHechoService hechoService) {
    this.hechoService = hechoService;
  }

  /* Devuelven un ResponseEntity
  * Y qué es el ResponseEntity? Qué hace?
  *   Lo que hace ResponseEntity es en envolver la rta. y darle un HTTP status code claro
  * Y para qué queremos eso?
  *   Queremos eso dado que es mas RESTful y flexible
  * */

  @DeleteMapping("/{id}")
  public ResponseEntity<HechoDTOEstatica> eliminarHecho(@PathVariable Long id ) {
    HechoDTOEstatica hecho = this.hechoService.marcarEliminadoHecho(id);
    if(hecho == null){
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(hecho);
  }

  @PostMapping
  public ResponseEntity<String> importar(@RequestParam("archivo") MultipartFile archivo, @RequestParam("idUsuario") Long idUsuario) {
      Set<HechoDTOEstatica> hechosImportados = hechoService.importarDesdeCSV(archivo, idUsuario);
      return ResponseEntity.ok("Importación exitosa. Se importaron " + hechosImportados.size() + " hechos.");
  }

  @GetMapping
  public ResponseEntity<ConjuntoHechoDTOEstatica> getAll() {
    Set<HechoDTOEstatica> todosLosHechos = hechoService.obtenerTodos();
    ConjuntoHechoDTOEstatica conjuntoHechos = new ConjuntoHechoDTOEstatica();
    conjuntoHechos.setHechos(todosLosHechos.stream().toList());
    return ResponseEntity.ok(conjuntoHechos);
  }


  @DeleteMapping
  public ResponseEntity<Void> eliminarTodo() {
    hechoService.eliminarTodo();
    return ResponseEntity.noContent().build();
  }

}

