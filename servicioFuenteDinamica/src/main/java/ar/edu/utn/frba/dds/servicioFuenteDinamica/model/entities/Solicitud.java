package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import java.util.List;
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
    private final Usuario usuario;
    @Getter
    @Setter
    private Long id;
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
