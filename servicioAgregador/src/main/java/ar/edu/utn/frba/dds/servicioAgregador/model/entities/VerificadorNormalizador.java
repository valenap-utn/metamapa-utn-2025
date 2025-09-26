package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.config.ColeccionComparaciones;
import ar.edu.utn.frba.dds.servicioAgregador.config.ComparacionDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.comparaciones.ComparacionHechos;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VerificadorNormalizador {
  private final List<ComparacionHechos> comparacionesAHacer;
  private final MapperComparaciones mapperComparaciones;
  
  public VerificadorNormalizador(ColeccionComparaciones comparacionesConfig, MapperComparaciones mapperComparaciones) {
    List<ComparacionDTO> comparaciones = comparacionesConfig.getComparaciones();
    this.mapperComparaciones = mapperComparaciones;
    this.comparacionesAHacer = new ArrayList<>();
    TFIDFCalculadoraPalabras tfidfCalculadora = new TFIDFCalculadoraPalabras();
    comparaciones.forEach(comparacion ->{
      ComparacionHechos comparacionGenerada = this.mapperComparaciones.crearComparacion(comparacion, tfidfCalculadora);
      this.comparacionesAHacer.add(comparacionGenerada);
    });

  }

  public boolean estaNormalizado(Hecho hecho, Hecho hechoExterno) {
    return this.comparacionesAHacer.stream().allMatch(comparacionHecho -> comparacionHecho.sonEquivalentes(hecho, hechoExterno));
  }
}
