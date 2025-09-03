package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import java.time.LocalDateTime;

public interface HechoDTO {
  Long getId();

  String getTitulo();

  String getDescripcion();

  Categoria getCategoria();

  Ubicacion getUbicacion();

  LocalDateTime getFechaAcontecimiento();

  LocalDateTime getFechaCarga();

  Long getIdUsuario();
}
