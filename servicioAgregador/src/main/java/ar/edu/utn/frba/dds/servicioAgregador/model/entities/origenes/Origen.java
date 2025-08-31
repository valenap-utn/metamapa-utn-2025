package ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Origen {
  private  TipoOrigen tipo;
  private  String url;
  private Long idExterno;
  private String nombreAPI;

  public boolean esIgual(Origen o){
    return tipo.equals(o.getTipo()) && url.equals(o.getUrl());
  }
}
