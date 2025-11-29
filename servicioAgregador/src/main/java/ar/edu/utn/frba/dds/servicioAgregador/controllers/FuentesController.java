package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.IFuenteColeccionRepositoryJPA;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/fuentes")
public class FuentesController {
  private final IFuenteColeccionRepositoryJPA repo;

  public FuentesController(IFuenteColeccionRepositoryJPA repo) {
    this.repo = repo;
  }

  @GetMapping("/cantidad")
  public ResponseEntity<Long> getCantidad() {
    return ResponseEntity.ok(this.repo.count());
  }

}
