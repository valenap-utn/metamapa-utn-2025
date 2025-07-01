package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import lombok.Getter;
import lombok.Setter;


public class Solicitud implements Revisable{
    @Getter
    @Setter
    private Hecho hecho;
    @Getter
    @Setter
    private String justificacion;
    @Setter
    @Getter
    private Estado estado;
    @Getter
    private Usuario usuario;
    @Getter
    @Setter
    private Long id;

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
}
