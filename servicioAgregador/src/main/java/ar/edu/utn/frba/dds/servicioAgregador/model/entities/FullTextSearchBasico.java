package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class FullTextSearchBasico implements IBuscadorFullTextSearch{
  private final TFIDFCalculadoraPalabras calculadoraPalabras = new TFIDFCalculadoraPalabras();

  @Override
  public String crearNombreNormalizadoCon(String nombreCategoria, List<String> categoriasHechos) {
    //Limpiar palabras
    Documento documentoNombre = Documento.ofStringSinNormalizar(nombreCategoria);
    List<Documento> todosLosDocumentos = categoriasHechos.stream().map(Documento::ofStringSinNormalizar).toList();

    //documento filtrado
    todosLosDocumentos.forEach(documento -> documento.calcularTFIDF(calculadoraPalabras));
    documentoNombre.calcularTFIDF(calculadoraPalabras);

    return String.join(" ", documentoFiltrado);
  }

}
