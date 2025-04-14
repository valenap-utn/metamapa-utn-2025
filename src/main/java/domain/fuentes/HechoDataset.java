package domain.fuentes;

import ar.edu.utn.frba.dds.Ubicacion;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class HechoDataset {
  private String titulo;
  private String categoria;
  private Ubicacion ubicacion;
  private LocalDateTime fechaDelHecho;

  public HechoDataset(String titulo, String categoria, Ubicacion ubicacion,
                      LocalDateTime fechaDelHecho) {
    this.titulo = titulo;
    this.fechaDelHecho = fechaDelHecho;
    this.categoria = categoria;
    this.ubicacion = ubicacion;
  }

  public boolean esIgualA(HechoDataset unHecho) {
    return this.titulo.equalsIgnoreCase(unHecho.getTitulo());
  }

}
