package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.servicios.FuenteEstaticaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/fuenteEstatica")
public class FuenteEstaticaController {
  private final FuenteEstaticaService fuenteEstaticaService;

  public FuenteEstaticaController(FuenteEstaticaService fuenteEstaticaService) {
    this.fuenteEstaticaService = fuenteEstaticaService;
  }

  @PostMapping(value = "/hechos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> subirHechosCSV(@RequestPart("archivo") MultipartFile archivo, @RequestPart("usuario") Long idUsuario, @RequestParam String baseUrl) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.fuenteEstaticaService.subirHechosCSV(archivo, idUsuario, baseUrl));
  }
}
