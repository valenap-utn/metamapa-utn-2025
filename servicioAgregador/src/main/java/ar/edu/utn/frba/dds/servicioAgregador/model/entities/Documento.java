package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import lombok.Getter;

public class Documento {
  @Getter
  private final List<String> tokens;

  public Documento() {
    tokens = new ArrayList<String>();
  }

  public static Documento ofStringSinNormalizar(String documentoALimpiar) {
    Documento doc = new Documento();
    doc.limpiarPalabrasYsetearlas(documentoALimpiar);
    return doc;
  }

  public void limpiarPalabrasYsetearlas(String documento) {
    String docuConSoloLetras = documento.replaceAll("[^a-zA-Z]", " ");
    String docuEnMinuscula = docuConSoloLetras.toLowerCase(Locale.ROOT);
    List<String> documentos  = Arrays.stream(docuEnMinuscula.split(" ")).toList();
    tokens.addAll(documentos);
  }
}
