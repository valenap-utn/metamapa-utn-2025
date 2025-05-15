package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.domain.entities.Usuario;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
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
    private Estado estado;
    @Getter
    private Usuario usuario;

    public Solicitud(Hecho hecho, Usuario usuario, String justificacion) {
        if (justificacion.length() < 500) {
            throw new IllegalArgumentException("La justificación debe tener como minimo 500 caracteres");
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
}
