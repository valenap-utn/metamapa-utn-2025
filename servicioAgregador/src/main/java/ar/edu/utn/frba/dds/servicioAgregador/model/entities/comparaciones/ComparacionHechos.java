package ar.edu.utn.frba.dds.servicioAgregador.model.entities.comparaciones;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.normalizacion.Documento;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import java.util.List;
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
    calculadora.recargarDocumentos(List.of(documentoDeInterno.getString(), documentoDeExterno.getString()));
    documentoDeExterno.calcularTFIDF(calculadora);
    documentoDeInterno.calcularTFIDF(calculadora);
    return documentoDeInterno.calcularCosenoConRespectoA(documentoDeExterno) > this.getCantidadAceptable();
  }

  protected abstract Documento elementoAComparar(Hecho hechoExterno);
}
