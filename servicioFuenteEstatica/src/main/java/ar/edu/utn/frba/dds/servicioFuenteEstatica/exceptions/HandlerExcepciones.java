package ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions;

import java.io.IOException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class HandlerExcepciones {
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ErrorDTO> exceptionHandler(Exception ex){
    System.out.println(ex.getMessage());
    System.out.println(Arrays.toString(ex.getStackTrace()));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("Error en el servidor", "Error Servidor"));
  }

  @ExceptionHandler(value = MaxUploadSizeExceededException.class)
  public ResponseEntity<ErrorDTO> handleMaximoEspacioSubidaSuperado(MaxUploadSizeExceededException ex){
    return ResponseEntity.badRequest().body(new ErrorDTO("Se ha superado el máximo espacio posible que se puede enviar. El máximo que puede tener un archivo son 50 MB", "Error en el maximo subida"));
  }

  @ExceptionHandler(value = UsuarioNoEncontrado.class)
  public ResponseEntity<ErrorDTO> handleUsuarioNoEncontrado(UsuarioNoEncontrado error) {
    return ResponseEntity.
            status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = ExcepcionIO.class)
  public ResponseEntity<ErrorDTO> handleIOException(ExcepcionIO error) {
    return ResponseEntity.status(400).body(new ErrorDTO(error.getMessage(), "Error IO"));
  }

  @ExceptionHandler(value = UsuarioSinPermiso.class)
  public ResponseEntity<ErrorDTO> handleUsuarioSinPermiso(UsuarioSinPermiso error) {
    return ResponseEntity.status(403).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }

  @ExceptionHandler(value = ArchivoVacioException.class)
  public ResponseEntity<ErrorDTO> handleArchivoVacio(ArchivoVacioException error) {
    return ResponseEntity.status(400).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }


  @ExceptionHandler(value = IOException.class)
  public ResponseEntity<ErrorDTO> handleArchivoVacio(IOException error) {
    return ResponseEntity.status(400).body(new ErrorDTO(error.getMessage(), "Error De I/O"));
  }

  @ExceptionHandler(value = HechoYaEstaEliminado.class)
  public ResponseEntity<ErrorDTO> handleHechoYaEstaEliminado(HechoYaEstaEliminado error) {
    return ResponseEntity.status(409).body(new ErrorDTO(error.getMessage(), error.getTipoError()));
  }
}
