package ar.edu.utn.frba.dds.servicioAgregador.model.entities;


import ar.edu.utn.frba.dds.servicioAgregador.exceptions.SolicitudError;
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
@NoArgsConstructor
@Entity
@Table(name = "solicitud")
public class Solicitud {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hecho_id", nullable = false, referencedColumnName = "id")
    @Getter @Setter private Hecho hecho;

    @Column(nullable = false, name = "justificacion")
    @Getter @Setter private String justificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    @Getter private Estado estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @Getter private Usuario usuario;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    public Solicitud(Hecho hecho, Usuario usuario, String justificacion) {
        if (justificacion.length() < 500) {
            throw new SolicitudError("La justificación debe tener como minimo 500 caracteres");
        }
        this.usuario = usuario;
        this.hecho = hecho;
        this.justificacion = justificacion;
        this.estado = Estado.PENDIENTE;
    }

    public void setEstado(Estado unEstado) {
        if(unEstado == Estado.ACEPTADA){
            this.hecho.setEliminado(true);
        }
        this.estado = unEstado;
    }

    public void aceptar() {
        this.estado = Estado.ACEPTADA;
    }

    public void rechazar() {
        this.estado = Estado.RECHAZADA;
    }

    public void marcarComoSpam() {
        this.estado = Estado.SPAM;
    }

    public boolean esAceptada() {
        return this.estado == Estado.ACEPTADA;
    }

    public boolean fueMarcadaComoSpam() {
        return this.estado == Estado.SPAM;
    }

  public boolean noEsPendiente() {
        return this.estado != Estado.PENDIENTE;
  }
}
