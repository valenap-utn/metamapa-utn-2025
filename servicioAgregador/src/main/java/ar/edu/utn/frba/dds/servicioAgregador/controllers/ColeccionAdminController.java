package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.ColeccionNoEncontrada;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.ErrorDTO;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.UsuarioSinPermiso;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioAgregador.services.IColeccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/colecciones")
public class ColeccionAdminController {
  private final IColeccionService coleccionService;

  public ColeccionAdminController(IColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }

  @PostMapping
  public ResponseEntity<ColeccionDTOOutput> crearColeccion(@RequestBody ColeccionDTOInput coleccion) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.coleccionService.crearColeccion(coleccion));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<ColeccionDTOOutput> cambiarAlgoritmoColeccion(@PathVariable String id, @RequestBody ColeccionDTOInput coleccion) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(this.coleccionService.cambiarAlgoritmo(coleccion, id));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @ExceptionHandler(value = ColeccionNoEncontrada.class)
  public ResponseEntity<ErrorDTO> handleColeccionNoEncontrada(ColeccionNoEncontrada error) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = UsuarioNoEncontrado.class)
  public ResponseEntity<ErrorDTO> handleUsuarioNoEncontrado(UsuarioNoEncontrado error) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = UsuarioSinPermiso.class)
  public ResponseEntity<ErrorDTO> handleUsuarioSinPermiso(UsuarioSinPermiso error) {
    return ResponseEntity.status(403).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }



}
