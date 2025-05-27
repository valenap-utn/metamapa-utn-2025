package ar.edu.utn.frba.dds.servicioFuenteEstatica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.services.HechoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/hechos")
public class HechoController {
  private final HechoService hechoService;

  @Autowired
  public HechoController(HechoService hechoService) {
    this.hechoService = hechoService;
  }

  @PostMapping("/importar")
  public ResponseEntity<List<HechoValueObject>> importarHechosDesdeCSV(@RequestParam("archivo") MultipartFile archivoCSV) {
    Set<HechoValueObject> hechos = hechoService.importarDesdeArchivo(archivoCSV);
    return ResponseEntity.ok(new ArrayList<>(hechos));
  }
}
