package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.entities.colecciones.Coleccion;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Categoria;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.ColeccionHechoValueObject;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.filtros.Criterio;
import ar.edu.utn.frba.dds.domain.filtros.FiltroNulo;
import ar.edu.utn.frba.dds.domain.filtros.FiltroPorCategoria;
import ar.edu.utn.frba.dds.domain.filtros.FiltroPorFechaAcontecimiento;
import ar.edu.utn.frba.dds.domain.filtros.FiltroPorTitulo;
import ar.edu.utn.frba.dds.domain.fuentes.estaticas.FuenteEstatica;
import ar.edu.utn.frba.dds.domain.fuentes.estaticas.ImportadorCSV;
import ar.edu.utn.frba.dds.utils.ConfigReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ColeccionTest {
  ImportadorCSV importador;
  FuenteEstatica fuente;
  ConfigReader lectorConfigs;
  Criterio criterio;
  Coleccion coleccionPrueba;
  Set<Hecho> hechos;

  @BeforeEach
  public void init() throws IOException {
    this.lectorConfigs = new ConfigReader("ArchivosCSV.properties");
    this.importador = new ImportadorCSV( this.lectorConfigs.getProperty("PATH") + "/CsvEscenarios.csv");
    this.fuente = new FuenteEstatica(this.importador);
    this.criterio = new Criterio();
    this.coleccionPrueba = new Coleccion("Colección prueba", "Esto es una prueba", this.fuente, this.criterio);
  }

  @Test
  @DisplayName("Agregar un criterio de pertenencia para que solo se carguen los hechos entre las fechas 01/01/2000 y 01/01/2010. Luego de recalcular los hechos de la colección, deberían quedar solo los primeros tres.\n" +
          "Sobre la misma colección, sumar otro criterio para que solo se carguen los hechos de categoría “Caída de aeronave”. Después de recalcular los hechos de la colección, el segundo ya no debería estar presente.\n")
  public void testEscenario1_2() {
    //Escenario 1.2
    criterio.agregarCriterios(new FiltroPorFechaAcontecimiento(LocalDate.parse("2000-01-01")
    ,LocalDate.parse("2010-01-01")));
    hechos = coleccionPrueba.busquedaCon(new FiltroNulo());
    Set<String> conjuntoTitulos = new HashSet<>();
    conjuntoTitulos.addAll(List.of("Caída de aeronave impacta en Venado Tuerto, Santa Fe", "Serio incidente: Accidente con maquinaria industrial en Chos Malal, Neuquén", "Caída de aeronave impacta en Olavarría"));
    Assertions.assertEquals(hechos.stream().map(Hecho::getTitulo).collect(Collectors.toSet()), conjuntoTitulos);
    criterio.agregarCriterios(new FiltroPorCategoria(new Categoria("Caída de aeronave")));
    hechos = coleccionPrueba.busquedaCon(new FiltroNulo());
    conjuntoTitulos.remove("Serio incidente: Accidente con maquinaria industrial en Chos Malal, Neuquén");
    Assertions.assertEquals(hechos.stream().map(Hecho::getTitulo).collect(Collectors.toSet()), conjuntoTitulos);
  }

  @Test
  @DisplayName("Sobre la colección aplicar un filtro de tipo categoría = “Caída de Aeronave” y título =”un título”.")
  public void testEscenario1_3() {
    hechos = coleccionPrueba.busquedaCon(new FiltroPorTitulo("unTitulo"), new FiltroPorCategoria(new Categoria("Caída de aeronave")));
    Assertions.assertTrue(hechos.isEmpty());
  }

  @Test
  @DisplayName("Etiquetar al hecho titulado “Caída de aeronave impacta en Olavarría” como “Olavarría” ." +
  "Etiquetar al mismo hecho como “Grave”.")
  public void testEscenario1_4() {
    hechos = coleccionPrueba.busquedaCon(new FiltroPorTitulo("Caída de aeronave impacta en Olavarría"));
    Hecho hechoOlavarria = hechos.stream().toList().get(0);
    hechoOlavarria.agregarEtiquetas("Olavarría", "Grave");
    Assertions.assertEquals(hechoOlavarria.getEtiquetas().stream().toList(), List.of("Olavarría", "Grave"));
  }
}
