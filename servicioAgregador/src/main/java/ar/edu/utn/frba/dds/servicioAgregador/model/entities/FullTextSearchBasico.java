package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FullTextSearchBasico implements IBuscadorFullTextSearch{
  private final TFIDFCalculadoraPalabras calculadoraPalabras = new TFIDFCalculadoraPalabras();

  @Override
  public String crearNombreNormalizadoCon(String nombreCategoria, List<String> categoriasHechos) {
    //Limpiar palabras
    Documento documentoNombre = Documento.ofStringSinNormalizar(nombreCategoria);
    int id = categoriasHechos.indexOf(nombreCategoria);
    List<Documento> todosLosDocumentos = categoriasHechos.stream().map(Documento::ofStringSinNormalizar).toList();

    //documento filtrado
    MatrixSimilitudCoseno matrixSimilitudCoseno = new MatrixSimilitudCoseno(todosLosDocumentos, calculadoraPalabras);
    matrixSimilitudCoseno.calcularCosenos();
    List<Documento> documentosConCoseno = matrixSimilitudCoseno.getByIdDeDocumentoEnFilaConCoseno(id);
    Comparator<Documento> comparador = Comparator.comparingDouble(Documento::getCosenoActual);
    Documento documentoGanador = documentosConCoseno.stream().max(comparador).orElse(null);
    return documentoGanador == null ? documentoNombre.getString() : documentoGanador.getString();
  }

}
