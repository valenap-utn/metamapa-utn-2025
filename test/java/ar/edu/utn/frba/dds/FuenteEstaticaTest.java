package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.ColeccionHechoValueObject;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.fuentes.estaticas.FuenteEstatica;
import ar.edu.utn.frba.dds.domain.fuentes.estaticas.ImportadorCSV;
import ar.edu.utn.frba.dds.utils.ConfigReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FuenteEstaticaTest {
  ImportadorCSV importador;
  FuenteEstatica fuente;
  ConfigReader lectorConfigs;
  @BeforeEach
  public void init() throws IOException {
    this.lectorConfigs = new ConfigReader("ArchivosCSV.properties");
    this.importador = new ImportadorCSV( this.lectorConfigs.getProperty("PATH") + "/CSVParaPruebas.csv");
    this.fuente = new FuenteEstatica(this.importador);
  }

  @Test
  @DisplayName("Buscamos ver si el conjunto de hechos que crea no tiene ningún repetido, ya que en el archivo hay un hecho repetido con la fecha cambiada del total de 5 hechos")
  public void testHechosSinRepeticion() {
    Set<Hecho> hechos = this.fuente.obtenerHechos();

    Assertions.assertEquals(hechos.size(), 4);
  }

  @Test
  @DisplayName("Buscamos ver si el conjunto de hechos se cargó correctamente, para eso vemos si los títulos coinciden")
  public void testHechosCargadosCorrectamente() {
    Set<Hecho> hechos = this.fuente.obtenerHechos();
    List<String> titulosDeHechos = new ArrayList<>();
    titulosDeHechos.addAll(List.of("Chivilcoy en alerta por Emanación de gas tóxico", "Descarrilamiento de tren causa estragos en Puerto Iguazú"
            , "Fallo en vuelo deja múltiples daños en Belén", "Apagón por tormenta deja múltiples daños en Mar del Plata, Buenos Aires"));

    for(int i=0 ; i<4; i++) {
      Set<String> titulosHechosObtenidos = hechos.stream().map(Hecho::getTitulo).collect(Collectors.toSet());
      Assertions.assertTrue(titulosHechosObtenidos.contains(titulosDeHechos.get(i)));
    }
  }
}
