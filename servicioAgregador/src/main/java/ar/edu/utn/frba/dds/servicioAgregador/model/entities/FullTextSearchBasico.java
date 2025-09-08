package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FullTextSearchBasico implements IBuscadorFullTextSearch{
  private final TFIDFCalculadoraPalabras calculadoraPalabras = new TFIDFCalculadoraPalabras();
  private final Double minimoAceptableCoseno = 0.5;

  @Override
  public String crearNombreNormalizadoCon(String nombreCategoria, List<String> categoriasHechos) {
    //Limpiar palabras
    Documento documentoNombre = Documento.ofStringSinNormalizar(nombreCategoria);
    int id = categoriasHechos.indexOf(nombreCategoria);
    List<Documento> todosLosDocumentos = categoriasHechos.stream().map(Documento::ofStringSinNormalizar).toList();

    //documento filtrado
    MatrixSimilitudCoseno matrixSimilitudCoseno = new MatrixSimilitudCoseno(todosLosDocumentos, calculadoraPalabras);
    matrixSimilitudCoseno.calcularCosenos();
    List<DocumentoConCoseno> documentosConCoseno = matrixSimilitudCoseno.getByIdDeDocumentoEnFilaConCoseno(id);
    Comparator<DocumentoConCoseno> comparador = Comparator.comparingDouble(DocumentoConCoseno::getCosenoDeSimilitud);
    DocumentoConCoseno documentoGanador = documentosConCoseno.stream().filter(documentoConCoseno -> documentoConCoseno.getCosenoDeSimilitud() >= this.minimoAceptableCoseno).max(comparador).orElse(null);
    return documentoGanador == null ? documentoNombre.getString() : documentoGanador.getString();
  }

}
