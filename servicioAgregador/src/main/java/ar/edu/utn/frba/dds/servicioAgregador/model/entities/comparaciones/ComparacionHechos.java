package ar.edu.utn.frba.dds.servicioAgregador.model.entities.comparaciones;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Documento;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ComparacionHechos {
  private TFIDFCalculadoraPalabras calculadora;
  private Double cantidadAceptable;

  public boolean sonEquivalentes(Hecho hecho, Hecho hechoExterno){

    Documento documentoDeExterno = this.elementoAComparar(hechoExterno);
    Documento documentoDeInterno = this.elementoAComparar(hecho);
    documentoDeExterno.calcularTFIDF(calculadora);
    documentoDeInterno.calcularTFIDF(calculadora);
    documentoDeInterno.calcularCosenoConRespectoA(documentoDeExterno);
    return documentoDeInterno.getCosenoActual() > this.getCantidadAceptable();
  }

  protected abstract Documento elementoAComparar(Hecho hechoExterno);
}
