//package ar.edu.utn.frba.dds.servicioFuenteEstatica.controllers;
//
//import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.ColeccionHecho;
//import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
//import ar.edu.utn.frba.dds.servicioFuenteEstatica.services.HechoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/colecciones")
//public class ColeccionHechoController {
//  private final HechoService hechoService;
//
//  @Autowired
//  public ColeccionHechoController(HechoService hechoService) {
//    this.hechoService = hechoService;
//  }
//
//  @PostMapping("/importar")
//  public ResponseEntity<String> importar(@RequestParam("archivo") MultipartFile archivo) {
//    try {
//      ColeccionHecho coleccion = hechoService.importarDesdeCSV(archivo);
//      return ResponseEntity.ok("Importación exitosa. ID de la colección: " + coleccion.getId());
//    } catch (IOException e) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//          .body("Error al procesar el archivo CSV.");
//    }
//  }
//
//  @GetMapping("/{id}")
//  public ResponseEntity<Set<Hecho>> getByColeccion(@PathVariable String id) {
//    return hechoService.buscarPorColeccion(id)
//        .map(ResponseEntity::ok)
//        .orElse(ResponseEntity.notFound().build());
//  }
//}
