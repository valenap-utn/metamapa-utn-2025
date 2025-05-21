package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.enums.EstadoSolicitud;
import lombok.Getter;
import lombok.Setter;


public class Solicitud {
    @Getter
    @Setter
    private Hecho hecho;
    @Getter
    @Setter
    private String justificacion;
    @Getter
    private EstadoSolicitud estado;
    @Getter
    private Usuario usuario;
    @Getter
    @Setter
    private Long id;

    public Solicitud(Hecho hecho, Usuario usuario, String justificacion) {
        if (justificacion.length() < 500) {
            throw new IllegalArgumentException("La justificación debe tener como minimo 500 caracteres");
        }
        this.usuario = usuario;
        this.hecho = hecho;
        this.justificacion = justificacion;
        this.estado = EstadoSolicitud.EN_REVISION;
    }

    public void setEstado(EstadoSolicitud unEstado) {
        if(unEstado == EstadoSolicitud.ACEPTADA){
            this.hecho.setEliminado(true);
        }
        this.estado = unEstado;
    }
}
