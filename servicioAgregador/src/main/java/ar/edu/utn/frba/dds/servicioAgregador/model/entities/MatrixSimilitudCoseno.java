package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import java.util.ArrayList;
import java.util.List;

public class MatrixSimilitudCoseno {
  private final List<List<Documento>> documentos;

  public MatrixSimilitudCoseno(List<Documento> documentosACargar, TFIDFCalculadoraPalabras calculadoraPalabras) {
    documentos = new ArrayList<>();
    List<Documento> documentoLista = new ArrayList<>(documentosACargar);
    for (int i = 0; i < documentosACargar.size(); i++) {
      List<Documento> documentosNuevos = new ArrayList<>();
      documentoLista.forEach(d -> documentosNuevos.add(Documento.ofDocumento(d)));
      documentosNuevos.forEach( docu -> docu.calcularTFIDF(calculadoraPalabras));
      documentos.add(documentosNuevos);
      Documento documentoAuxiliar = documentoLista.remove(0);
      documentoLista.add(documentoAuxiliar);
    }
  }

  public void calcularCosenos() {
    this.documentos.forEach(this::calcularCosenosDeFila);
  }

  private void calcularCosenosDeFila(List<Documento> filaDocumento) {
    Documento documentoPrimero = filaDocumento.get(0);
    filaDocumento.forEach( documento -> documento.calcularCosenoConRespectoA(documentoPrimero));
  }


  public List<Documento> getByIdDeDocumentoEnFilaConCoseno(int id) {
    List<Documento> filaDocumento = new ArrayList<>(documentos.get(id));
    filaDocumento.remove(0); //Para sacar el documento igual
    return filaDocumento;
  }
}
