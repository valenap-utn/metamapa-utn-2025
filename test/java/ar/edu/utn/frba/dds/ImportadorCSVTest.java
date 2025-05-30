package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Categoria;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.ColeccionHechoValueObject;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.HechoValueObject;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Ubicacion;
import ar.edu.utn.frba.dds.domain.fuentes.estaticas.ImportadorCSV;
import ar.edu.utn.frba.dds.utils.ConfigReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ImportadorCSVTest {
  ImportadorCSV importador;
  ColeccionHechoValueObject hechosValueObjectObtenidos;
  ConfigReader lectorConfigs;

  @BeforeEach
  public void init() throws IOException {
    this.lectorConfigs = new ConfigReader("ArchivosCSV.properties");
    this.importador = new ImportadorCSV( this.lectorConfigs.getProperty("PATH") + "/CSVParaPruebas.csv");
  }

  @Test
  @DisplayName("Se busca una prueba sencilla para ver si puede importar 1 fila de un csv y logra transformarlas bien")
  public void probarfuncionalidadImportacion() {
    this.hechosValueObjectObtenidos = this.importador.importarHechosDataset();
    ColeccionHechoValueObject coleccionNueva = new ColeccionHechoValueObject();
    HechoValueObject hecho1 = new HechoValueObject("Chivilcoy en alerta por Emanación de gas tóxico",
            "Grave emanación de gas tóxico ocurrió en las inmediaciones de Chivilcoy, Buenos Aires. El incidente generando caos en el transporte público y privado. Defensa Civil coordina las tareas de asistencia y reconstrucción.",
            new Categoria("Emanación de gas tóxico"),
            new Ubicacion(-60.035774f, -34.914536f),
            LocalDate.parse("2022-06-07"));
    coleccionNueva.agregarHechosDataset(hecho1);

    Assertions.assertEquals(hechosValueObjectObtenidos.getHechosDataset().get(0).getTitulo(), coleccionNueva.getHechosDataset().get(0).getTitulo());
    Assertions.assertEquals(hechosValueObjectObtenidos.getHechosDataset().get(0).getDescripcion(), coleccionNueva.getHechosDataset().get(0).getDescripcion());
    Assertions.assertEquals(hechosValueObjectObtenidos.getHechosDataset().get(0).getCategoria().getNombre(), coleccionNueva.getHechosDataset().get(0).getCategoria().getNombre());
    Assertions.assertEquals(hechosValueObjectObtenidos.getHechosDataset().get(0).getUbicacion().getLongitud(), coleccionNueva.getHechosDataset().get(0).getUbicacion().getLongitud());
    Assertions.assertEquals(hechosValueObjectObtenidos.getHechosDataset().get(0).getUbicacion().getLatitud(), coleccionNueva.getHechosDataset().get(0).getUbicacion().getLatitud());
    Assertions.assertEquals(hechosValueObjectObtenidos.getHechosDataset().get(0).getFechaAcontecimiento(), coleccionNueva.getHechosDataset().get(0).getFechaAcontecimiento());
  }
}
