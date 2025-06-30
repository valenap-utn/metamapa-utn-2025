package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.EstadoSolicitud;
import lombok.Getter;
import lombok.Setter;


public class Solicitud {
    @Getter
    @Setter
    private Hecho hecho;
    @Getter
    @Setter
    private String justificacion;
    @Setter
    @Getter
    private EstadoSolicitud estado;
    @Getter
    private Usuario usuario;
    @Getter
    @Setter
    private Long id;

    public Solicitud(Hecho hecho, Usuario usuario, String justificacion) {
        this.usuario = usuario;
        this.hecho = hecho;
        this.justificacion = justificacion;
        this.estado = EstadoSolicitud.EN_REVISION;
    }

}
