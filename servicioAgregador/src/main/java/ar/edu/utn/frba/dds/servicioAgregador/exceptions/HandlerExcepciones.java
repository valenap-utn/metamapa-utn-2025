package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExcepciones {
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ErrorDTO> exceptionHandler(Exception ex){
    System.out.println("Nombre excepcion: " + ex.getClass().getName());
    System.out.println("Mensaje excepcion: " + ex.getMessage());
    System.out.println("Fecha y hora de la excepcion: " + LocalDateTime.now());
    System.out.println("Stack trace de la excepcion: ");
    Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).forEach(System.out::println);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Error en el servidor", "Error Servidor"));
  }

  @ExceptionHandler(value = ColeccionNoEncontrada.class)
  public ResponseEntity<ErrorDTO> handleColeccionNoEncontrada(ColeccionNoEncontrada error) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = UsuarioNoEncontrado.class)
  public ResponseEntity<ErrorDTO> handleUsuarioNoEncontrado(UsuarioNoEncontrado error) {
    return ResponseEntity.status(401).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = UsuarioSinPermiso.class)
  public ResponseEntity<ErrorDTO> handleUsuarioSinPermiso(UsuarioSinPermiso error) {
    return ResponseEntity.status(403).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = SolicitudError.class)
  public ResponseEntity<ErrorDTO> handleSolicitudError(SolicitudError error) {
    return ResponseEntity.status(400).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    return ResponseEntity.status(400).body(new ErrorDTO("Hay un error en el cuerpo de la request realizada", "Error en body"));
  }

  @ExceptionHandler(value = HechoNoEncontrado.class)
  public ResponseEntity<ErrorDTO> handleHechoNoEncontrado(HechoNoEncontrado error) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = SolicitudNoEncontrada.class)
  public ResponseEntity<ErrorDTO> handleSolicitudNoEncontrada(SolicitudNoEncontrada error) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = HechoYaEliminado.class)
  public ResponseEntity<ErrorDTO> handleHechoYaEliminado(HechoNoEncontrado error) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }
}
