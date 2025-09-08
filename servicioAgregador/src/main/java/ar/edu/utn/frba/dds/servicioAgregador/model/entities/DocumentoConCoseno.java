package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoConCoseno {
  private Double cosenoDeSimilitud;
  private Documento documento;

  public DocumentoConCoseno(Double aDouble, Documento aDocumento) {
    cosenoDeSimilitud = aDouble;
    documento = aDocumento;
  }

  public String getString() {
    return this.documento.getString();
  }
}
