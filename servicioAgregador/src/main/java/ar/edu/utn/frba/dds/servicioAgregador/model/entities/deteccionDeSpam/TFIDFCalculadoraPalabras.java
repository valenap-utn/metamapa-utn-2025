package ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam;

import ar.edu.utn.frba.dds.servicioAgregador.config.ColeccionDeDocumentosSpam;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TFIDFCalculadoraPalabras {
  private final List<List<String>> documentosSpam;

  public TFIDFCalculadoraPalabras() {
    documentosSpam = new ArrayList<>();
  }

  public void recargarDocumentos(List<String> documentos) {
    documentos.forEach(texto -> this.documentosSpam.add(this.prepararDocumento(texto)));
  }
  //Para Testing
  public TFIDFCalculadoraPalabras(List<String> documentos) {
    this.documentosSpam = new ArrayList<>();
    documentos.forEach(texto -> this.documentosSpam.add(this.prepararDocumento(texto)));
  }

  public TFIDFCalculadoraPalabras(ColeccionDeDocumentosSpam coleccion) {
    List<String> documentos = coleccion.getDocumentos();
    this.documentosSpam = new ArrayList<>();
    documentos.forEach(texto -> this.documentosSpam.add(this.prepararDocumento(texto)));
  }

  public List<String> prepararDocumento(String documento) {
    String documentoSinAcentos = documento.replace('ñ', '\001');
    documentoSinAcentos = documentoSinAcentos.replace('Ñ', '\002');
    documentoSinAcentos = Normalizer.normalize(documentoSinAcentos, Normalizer.Form.NFD);
    documentoSinAcentos = documentoSinAcentos.replaceAll("\\p{InCombiningDiacriticalMarks}", "");
    documentoSinAcentos = documentoSinAcentos.replace('\001', 'ñ');
    documentoSinAcentos = documentoSinAcentos.replace('\002', 'Ñ');
    String docuConSoloLetras = documentoSinAcentos.replaceAll("[^a-zA-ZñÑ0-9]", " ");
    String docuEnMinuscula = docuConSoloLetras.toLowerCase(Locale.ROOT);
    return Arrays.stream(docuEnMinuscula.split(" ")).toList();
  }

  public Double calcularTFIDFDePalabra(List<String> documento, String palabra) {
    return calcularTFPalabra(documento, palabra) * calcularIDFPalabra(palabra);
  }

  private Double calcularTFPalabra(List<String> documento, String palabra) {
    Long cantidadAparicionesPalabra = this.cantRepeticionesPalabraEnDocumento(documento, palabra);
    return cantidadAparicionesPalabra.doubleValue() / documento.size();
  }

  private Long cantRepeticionesPalabraEnDocumento(List<String> documento, String palabra) {
    return documento.stream().filter(palabraDocu -> palabraDocu.equalsIgnoreCase(palabra)).count();
  }

  private Double calcularIDFPalabra(String palabra) {
    long cantDocusConPalabra = this.documentosSpam.stream().filter(documento -> this.cantRepeticionesPalabraEnDocumento(documento, palabra) > 0).count();
    if (cantDocusConPalabra  == 0) {
      cantDocusConPalabra = 1L;
    }
    return Math.log10(this.documentosSpam.size() / (double) cantDocusConPalabra);
  }
}
