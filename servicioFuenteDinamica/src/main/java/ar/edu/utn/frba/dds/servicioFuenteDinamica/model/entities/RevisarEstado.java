package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.EstadoInexistente;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import org.springframework.stereotype.Component;

@Component
public class RevisarEstado {
  public Estado revisar(Revisable elemento, String estadoStr, String comentario) {
    try {
      Estado estado = Estado.valueOf(estadoStr);
      elemento.setEstado(estado);
      elemento.setComentarioRevision(comentario);
      return estado;
    } catch (IllegalArgumentException e) {
      throw new EstadoInexistente("El estado pasado para el elemento no existe");
    }
  }
}
