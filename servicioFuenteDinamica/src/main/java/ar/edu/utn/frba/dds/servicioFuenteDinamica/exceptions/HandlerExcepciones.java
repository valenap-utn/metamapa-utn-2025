package ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerExcepciones {
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ErrorDTO> exceptionHandler(Exception ex){
    System.out.println(ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Error en el servidor", "Error Servidor"));
  }

  @ExceptionHandler(value = UsuarioNoEncontrado.class)
  public ResponseEntity<ErrorDTO> handleUsuarioNoEncontrado(UsuarioNoEncontrado error) {
    return ResponseEntity.status(401).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = UsuarioSinPermiso.class)
  public ResponseEntity<ErrorDTO> handleUsuarioSinPermiso(UsuarioSinPermiso error) {
    return ResponseEntity.status(403).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = HechoNoEncontrado.class)
  public ResponseEntity<ErrorDTO> handleHechoNoEncontrado(HechoNoEncontrado error) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }
}
