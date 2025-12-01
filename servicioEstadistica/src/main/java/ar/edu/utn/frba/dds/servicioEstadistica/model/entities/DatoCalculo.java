package ar.edu.utn.frba.dds.servicioEstadistica.model.entities;

import java.util.List;
import lombok.Data;

@Data
public class DatoCalculo {
  private List<Hecho> hechos;
  private List<Solicitud> solicitudes;
  private List<String> titulosColeccion;
}
