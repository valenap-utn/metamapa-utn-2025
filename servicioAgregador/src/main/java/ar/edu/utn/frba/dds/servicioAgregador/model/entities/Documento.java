package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Documento {
  private final List<String> tokens;
  private final List<Double> medicionesTFIDF;
  private Double cosenoActual;

  public Documento() {
    this.medicionesTFIDF = new ArrayList<>();
    tokens = new ArrayList<>();
  }

  public static Documento ofStringSinNormalizar(String documentoALimpiar) {
    Documento doc = new Documento();
    doc.limpiarPalabrasYsetearlas(documentoALimpiar);
    return doc;
  }

  public static Documento ofDocumento(Documento d) {
    Documento doc = new Documento();
    doc.setTokens(d.getTokens());
    return doc;
  }

  private void setTokens(List<String> tokens) {
    this.tokens.clear();
    this.tokens.addAll(tokens);
  }

  public void limpiarPalabrasYsetearlas(String documento) {
    String docuConSoloLetras = documento.replaceAll("[^a-zA-Z]", " ");
    String docuEnMinuscula = docuConSoloLetras.toLowerCase(Locale.ROOT);
    List<String> documentos  = Arrays.stream(docuEnMinuscula.split(" ")).toList();
    tokens.addAll(documentos);
  }

  public void calcularTFIDF(TFIDFCalculadoraPalabras calculadoraPalabras) {
    calculadoraPalabras.recargarDocumentos(tokens);
    this.medicionesTFIDF.addAll(this.tokens.stream().map(token -> calculadoraPalabras.calcularTFIDFDePalabra(this.tokens, token)).toList());
  }

  public Double calcularCosenoConRespectoA(Documento documentoPrimero) {
    double acumulador = 0.0;
    for(int i=0; i<this.tokens.size(); i++) {
      acumulador += documentoPrimero.getMedicion(i) * this.medicionesTFIDF.get(i);
    }
    return (acumulador)/(this.normaDocumento() * documentoPrimero.normaDocumento());
  }

  private Double normaDocumento() {
    return Math.sqrt(this.medicionesTFIDF.stream().map(valor -> valor*valor).reduce(0.0, Double::sum));
  }

  private Double getMedicion(int i) {
    if (i >= this.tokens.size())
      return 0.0;
    return this.medicionesTFIDF.get(i);
  }

  public String getString() {
    return String.join(" ", tokens);
  }
}
