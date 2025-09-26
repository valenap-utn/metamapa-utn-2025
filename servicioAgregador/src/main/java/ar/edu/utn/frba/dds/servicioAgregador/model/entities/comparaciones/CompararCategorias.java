package ar.edu.utn.frba.dds.servicioAgregador.model.entities.comparaciones;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.normalizacion.Documento;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public class CompararCategorias extends ComparacionHechos{

  public CompararCategorias(TFIDFCalculadoraPalabras calculadora, Double cantidadAceptable) {
    super(calculadora, cantidadAceptable);
  }

  @Override
  protected Documento elementoAComparar(Hecho hechoExterno) {
    return Documento.ofStringSinNormalizar(hechoExterno.getNombreCategoria());
  }
}
