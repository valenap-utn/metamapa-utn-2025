package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/fuenteEstatica")
public class FuenteEstaticaController {
  @PostMapping("/hechos")
  public String subirHechosCSV(MultipartFile archivo, Long idUsuario, String baseURL) {

  }
}
