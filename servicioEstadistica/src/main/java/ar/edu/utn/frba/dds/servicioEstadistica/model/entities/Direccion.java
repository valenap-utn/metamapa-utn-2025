package ar.edu.utn.frba.dds.servicioEstadistica.model.entities;

import lombok.Data;

@Data
public class Direccion {
  private String ciudad;
  private String localidad;
  private String municipio;
  private String provincia;
}
