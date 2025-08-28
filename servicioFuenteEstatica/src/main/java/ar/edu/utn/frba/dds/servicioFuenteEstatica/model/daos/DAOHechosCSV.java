package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.daos;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Ubicacion;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component; //bean

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class DAOHechosCSV implements IHechosDAO {

  @Override
  public Set<HechoValueObject> getAll(InputStream inputStream) {
    //declaro el Set de hechos
    Set<HechoValueObject> hechos = new HashSet<>();

    try(Reader reader = new InputStreamReader(inputStream)) {
      //open del file csv
      CsvToBean<HechoCSV> csvToBean = new CsvToBeanBuilder<HechoCSV>(reader)
          .withType(HechoCSV.class)
          .withIgnoreLeadingWhiteSpace(true)
          .build();

      //leo las filas
      for(HechoCSV fila : csvToBean) {
        //Por cada fila instanciar un HechoValueObject y asignarle la info que tomas de cada fila
        HechoValueObject hecho = mapearDesdeCSV(fila);
        hechos.add(hecho); //agrego el VO a la collection hechos
      }

      return hechos; //return hechos

    } catch (IOException e) {
      throw new RuntimeException("Error leyendo el CSV", e);
    }

  }

  private HechoValueObject mapearDesdeCSV(HechoCSV fila) {
    LocalDate fechaAcont = LocalDate.parse(fila.getFechaAcontecimiento());
    Categoria categoria = new Categoria(fila.getCategoria());
    Ubicacion ubicacion = new Ubicacion(fila.getLongitud(), fila.getLatitud());
    return HechoValueObject.of(fila.getTitulo(), fila.getDescripcion(), fechaAcont, categoria, ubicacion);
  }
}
