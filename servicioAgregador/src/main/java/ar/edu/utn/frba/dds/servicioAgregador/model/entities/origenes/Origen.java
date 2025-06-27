package ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes;


import lombok.Getter;

@Getter
public class Origen {
  private TipoOrigen tipo;
  private String url;

  public Origen(TipoOrigen tipo, String url) {
    this.tipo = tipo;
    this.url = url;
  }
}
