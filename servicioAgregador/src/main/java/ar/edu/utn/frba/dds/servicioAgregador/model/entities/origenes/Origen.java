package ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "origen")
public class Origen {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo", nullable = false)
  private TipoOrigen tipo;

  @Column(name = "url", nullable = false)
  private String url;
  @Column(name = "id_externo")
  private Long idExterno;
  @Column(name = "nombreAPI")
  private String nombreAPI;

  public boolean esIgual(Origen o){
    return tipo.equals(o.getTipo()) && url.equals(o.getUrl());
  }
}
