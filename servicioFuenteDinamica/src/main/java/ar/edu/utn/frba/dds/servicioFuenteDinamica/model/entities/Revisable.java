package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;

public interface Revisable {
  void setEstado(Estado estado);
  void setComentarioRevision(String comentario);
}
