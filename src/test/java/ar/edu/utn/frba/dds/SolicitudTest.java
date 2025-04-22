package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.entities.colecciones.Coleccion;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.filtros.Criterio;
import ar.edu.utn.frba.dds.domain.filtros.FiltroNulo;
import ar.edu.utn.frba.dds.domain.fuentes.estaticas.FuenteEstatica;
import ar.edu.utn.frba.dds.domain.fuentes.estaticas.ImportadorCSV;
import ar.edu.utn.frba.dds.domain.solicitud.Estado;
import ar.edu.utn.frba.dds.domain.solicitud.Solicitud;
import ar.edu.utn.frba.dds.utils.ConfigReader;
import java.io.IOException;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SolicitudTest {
  ImportadorCSV importador;
  FuenteEstatica fuente;
  ConfigReader lectorConfigs;
  Criterio criterio;
  Coleccion coleccionPrueba;
  Hecho hecho;
  Solicitud solicitud;

  @BeforeEach
  public void init() throws IOException {
    this.lectorConfigs = new ConfigReader("ArchivosCSV.properties");
    this.importador = new ImportadorCSV( this.lectorConfigs.getProperty("PATH") + "/CSVEscenario3.csv");
    this.fuente = new FuenteEstatica(this.importador);
    this.criterio = new Criterio();
    this.coleccionPrueba = new Coleccion("Colección prueba", "Esto es una prueba", this.fuente, this.criterio);
    Set<Hecho> hechos = this.coleccionPrueba.busquedaCon(new FiltroNulo());
    this.hecho = hechos.stream().toList().get(0);
    String textoPrueba = "Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba Texto Prueba";
    this.solicitud = new Solicitud(this.hecho, textoPrueba);
  }

  @Test
  @DisplayName("""
          Crear una solicitud de eliminación asociada a este hecho. Quedará en estado pendiente.\s
          El motivo de esta solicitud deberá tener al menos 500 caracteres.
          Rechazar esta solicitud un día después de su creación. Dado que fue rechazada, el hecho puede ser agregado a cualquier colección.\s
          Verificar que la solicitud haya quedado en estado rechazada.
          """)
  public void testEscenario3_1() {
    this.solicitud.setEstado(Estado.RECHAZADA);
    Assertions.assertEquals(Estado.RECHAZADA, solicitud.getEstado());
    Assertions.assertFalse(this.coleccionPrueba.busquedaCon(new FiltroNulo()).isEmpty());
  }

  @Test
  @DisplayName("""
          Generar otra solicitud para el mismo hecho.
          Ahora esta solicitud es aceptada a las 2 horas. Esta vez el hecho no debería poder agregarse a una colección, puesto que este fue eliminado.
          Verificar que la solicitud haya quedado en estado aceptada.
          """)
  public void testEscenario3_2() {
    this.solicitud.setEstado(Estado.ACEPTADA);
    Assertions.assertEquals(Estado.ACEPTADA, this.solicitud.getEstado());
    Assertions.assertTrue(this.coleccionPrueba.busquedaCon(new FiltroNulo()).isEmpty());
  }
}
