package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;

public class RevisarEstado {
  public Estado revisar(Revisable elemento, String estadoStr, String comentario) {
    Estado estado = Estado.valueOf(estadoStr);
    elemento.setEstado(estado);
    elemento.setComentarioRevision(comentario);
    return estado;
  }
}
