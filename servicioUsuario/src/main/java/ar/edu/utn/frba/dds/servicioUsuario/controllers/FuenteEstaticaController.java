package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.servicios.FuenteEstaticaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/fuenteEstatica")
public class FuenteEstaticaController {
  private final FuenteEstaticaService fuenteEstaticaService;

  public FuenteEstaticaController(FuenteEstaticaService fuenteEstaticaService) {
    this.fuenteEstaticaService = fuenteEstaticaService;
  }

  @PostMapping("/hechos")
  public String subirHechosCSV(@RequestParam MultipartFile archivo,@RequestParam Long idUsuario, @RequestParam String baseURL) {
    return this.fuenteEstaticaService.subirHechosCSV(archivo, idUsuario, baseURL);
  }
}
