package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import java.time.LocalDate;

public interface HechoDTO {
  Long getId();

  String getTitulo();

  String getDescripcion();

  Categoria getCategoria();

  Ubicacion getUbicacion();

  LocalDate getFechaAcontecimiento();

  LocalDate getFechaCarga();
}
