package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Estandarizador {
  public List<Hecho> estandarizar(List<Hecho> hechos) {
    List<Hecho> hechosEstandarizados = new ArrayList<Hecho>(hechos);
    hechosEstandarizados.forEach(this::estandarizarFecha);
    hechosEstandarizados.forEach(hecho -> estandarizarUbicacion(hecho, hechos));
    hechosEstandarizados.forEach(hecho -> estandarizarCategoria(hecho, hechos));
    hechosEstandarizados.forEach(hecho -> estandarizarTitulo(hecho, hechos));
    return hechosEstandarizados;
  }

  private void estandarizarTitulo(Hecho hecho, List<Hecho> hechos) {
  }

  private void estandarizarCategoria(Hecho hecho, List<Hecho> hechos) {
  }

  private void estandarizarUbicacion(Hecho hecho, List<Hecho> hechos) {
  }

  private void estandarizarFecha(Hecho hecho) {
  }
}