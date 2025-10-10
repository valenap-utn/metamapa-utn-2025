package ar.edu.utn.frba.dds.servicioUsuario.exceptions;

import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerExceptions {
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

  @ExceptionHandler(value = AdministradorInvalido.class)
  public ResponseEntity<ErrorDTO> handleAdministradorInvalido(AdministradorInvalido error) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = UsuarioInvalido.class)
  public ResponseEntity<ErrorDTO> handleUsuarioInvalido(UsuarioInvalido error) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = UsuarioConflicto.class)
  public ResponseEntity<ErrorDTO> handleUsuarioConflicto(UsuarioConflicto error) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

}
