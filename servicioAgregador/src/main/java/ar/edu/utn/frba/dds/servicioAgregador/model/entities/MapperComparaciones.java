package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.config.ComparacionDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.comparaciones.ComparacionHechos;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.comparaciones.CompararCategorias;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.comparaciones.CompararDirecciones;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.comparaciones.CompararTitulos;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import org.springframework.stereotype.Component;

@Component
public class MapperComparaciones {

  public ComparacionHechos crearComparacion(ComparacionDTO comparacion, TFIDFCalculadoraPalabras tfidfCalculadora) {
    if(comparacion.getTipo().equalsIgnoreCase("TITULO")) {
      return new CompararTitulos(tfidfCalculadora, comparacion.getCantidad());
    } else if (comparacion.getTipo().equalsIgnoreCase("CATEGORIA")) {
      return new CompararCategorias(tfidfCalculadora, comparacion.getCantidad());
    } else if (comparacion.getTipo().equalsIgnoreCase("DIRECCION")) {
      return new CompararDirecciones(tfidfCalculadora, comparacion.getCantidad());
    }
    return null;
  }
}
