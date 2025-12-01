package ar.edu.utn.frba.dds.servicioEstadistica.exceptions;

import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerExcepciones {
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ErrorDTO> exceptionHandler(Exception ex){
    System.out.println("Nombre excepcion: " + ex.getClass().getName());
    System.out.println("Mensaje excepcion: " + ex.getMessage());
    System.out.println("Fecha y hora de la excepcion: " + LocalDateTime.now());
    System.out.println("Stack trace de la excepcion: ");
    Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).forEach(System.out::println);
    System.out.println("\n");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Error en el servidor", "Error Servidor"));
  }

  @ExceptionHandler(value = IllegalStateException.class)
  public ResponseEntity<ErrorDTO> illegalStateExceptionHandler(IllegalStateException error){
    return ResponseEntity.status(400).body(new ErrorDTO(error.getMessage(), "estado Ilegal"));
  }


  @ExceptionHandler(value = UsuarioNoEncontrado.class)
  public ResponseEntity<ErrorDTO> handleUsuarioNoEncontrado(UsuarioNoEncontrado error) {
    return ResponseEntity.status(401).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }


  @ExceptionHandler(value = UsuarioSinPermiso.class)
  public ResponseEntity<ErrorDTO> handleUsuarioSinPermiso(UsuarioSinPermiso error) {
    return ResponseEntity.status(403).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }
}
