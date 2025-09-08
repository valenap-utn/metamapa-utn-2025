package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import java.util.ArrayList;
import java.util.List;

public class MatrixSimilitudCoseno {
  private final List<List<DocumentoConCoseno>> documentos;
  private final List<Documento> documentosATrabajar;

  public MatrixSimilitudCoseno(List<Documento> documentosACargar, TFIDFCalculadoraPalabras calculadoraPalabras) {
    documentos = new ArrayList<>();
    calculadoraPalabras.recargarDocumentos(documentosACargar.stream().map(Documento::getString).toList());
    this.documentosATrabajar = new ArrayList<>(documentosACargar);
    this.documentosATrabajar.forEach(documento -> documento.calcularTFIDF(calculadoraPalabras));
  }

  public void calcularCosenos() {
    List<Documento> documentosCopia = new ArrayList<>(this.documentosATrabajar);
    this.documentosATrabajar.forEach(documento -> this.calcularCosenosDeFila(documento, documentosCopia));
  }

  private void calcularCosenosDeFila(Documento documentoDeReferencia, List<Documento> filaDocumento) {
    List<DocumentoConCoseno> cosenosDeFila = new ArrayList<>();
    filaDocumento.forEach( documento -> cosenosDeFila.add(
            new DocumentoConCoseno(documentoDeReferencia.calcularCosenoConRespectoA(documento), documento)));
    this.documentos.add(cosenosDeFila);
    Documento primerDocumento = filaDocumento.remove(0);
    filaDocumento.add(primerDocumento);
  }


  public List<DocumentoConCoseno> getByIdDeDocumentoEnFilaConCoseno(int id) {
    List<DocumentoConCoseno> filaDocumento = new ArrayList<>(documentos.get(id));
    filaDocumento.remove(0); //Para sacar el documento igual
    return filaDocumento;
  }
}
