package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "solicitud")
public class Solicitud implements Revisable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hecho_id", nullable = false, referencedColumnName = "id")
    @Getter @Setter private Hecho hecho;

    @Column(nullable = false, name = "justificacion")
    @Getter @Setter private String justificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    @Setter @Getter private Estado estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    @Getter private final Usuario usuario;

//    @Column(name = "fue_usada")
    private boolean fueUsada = false;

    public Solicitud(Hecho hecho, Usuario usuario, String justificacion) {
        this.usuario = usuario;
        this.hecho = hecho;
        this.justificacion = justificacion;
        this.estado = Estado.EN_REVISION;
    }

    @Override
    public void setComentarioRevision(String comentario) {
        this.justificacion = comentario;
    }

  public boolean estaAceptada() {
        return List.of(Estado.ACEPTADA, Estado.ACEPTADA_CON_CAMBIOS).contains(this.estado);
  }

  public boolean noFueUsada() {
        return !this.fueUsada;
  }

  public void usar() {
        this.fueUsada = true;
  }

}
