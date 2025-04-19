package ar.edu.utn.frba.dds.domain.solicitud;

import ar.edu.utn.frba.dds.domain.entities.Usuario;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

import java.time.LocalDateTime;

public class Solicitud {
    private Usuario usuario;
    private Hecho hecho;
    private String justificacion;
    private Estado estado;

    public Solicitud(Hecho hecho, Usuario persona, String justificacion) {
        if (justificacion.length() < 500) {
            throw new IllegalArgumentException("La justificación debe tener como minimo 500 caracteres");
        }
        this.hecho = hecho;
        this.usuario = persona;
        this.justificacion = justificacion;
        this.estado = Estado.PENDIENTE;
    }

    public void aceptar() {

        this.estado = Estado.ACEPTADA;
    }

    public void rechazar() {
        if (this.estado != Estado.PENDIENTE) {
            throw new IllegalStateException("Unicamente se pueden rechazar solicitudes pendientes");
        }
        this.estado = Estado.RECHAZADA;
    }

}
