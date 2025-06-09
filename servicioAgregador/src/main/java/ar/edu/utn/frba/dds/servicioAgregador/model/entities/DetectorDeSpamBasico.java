package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;

public class DetectorDeSpamBasico implements DetectorDeSpam{
  private TFIDFCalculadoraPalabras calculadoraTFIDF;
  @Setter
  @Getter
  private Double nivelImportanciaMinimo;

  public DetectorDeSpamBasico(TFIDFCalculadoraPalabras calculadoraTFIDF, Double nivelImportanciaMinimo) {
    this.calculadoraTFIDF = calculadoraTFIDF;
    this.nivelImportanciaMinimo = nivelImportanciaMinimo;
  }

  @Override
  public boolean esSpam(String texto) {
    List<String> palabrasDelTexto = this.calculadoraTFIDF.prepararDocumento(texto);
    return palabrasDelTexto.stream().allMatch(palabra -> this.esPalabraPocoImportante(palabrasDelTexto, palabra));
  }

  private boolean esPalabraPocoImportante(List<String> documento, String palabra) {
    Double importanciaPalabra = this.calculadoraTFIDF.calcularTFIDFDePalabra(documento, palabra);
    return importanciaPalabra < nivelImportanciaMinimo;
  }
}
