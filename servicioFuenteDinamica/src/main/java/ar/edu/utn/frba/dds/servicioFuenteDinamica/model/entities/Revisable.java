package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;

public interface Revisable {
  public void setEstado(Estado estado);
  public void setComentarioRevision(String comentario);
}
