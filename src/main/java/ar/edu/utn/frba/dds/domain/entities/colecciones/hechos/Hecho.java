package ar.edu.utn.frba.dds.domain.entities.colecciones.hechos;

import ar.edu.utn.frba.dds.domain.filtros.Criterio;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Hecho {
    private HechoValueObject infoHecho;
    private LocalDateTime fechaCarga;
    private boolean esCargaManual;
    private Origen origen; //(?TODO: ponerlo en clases como dijeron los ayudantes
    private boolean eliminado;

    public Hecho(HechoValueObject infoHecho, Origen origen) {
        this.infoHecho = infoHecho;
        this.origen = origen;
        this.fechaCarga = LocalDateTime.now();
        this.eliminado = false;
    }

    public boolean estaEliminado() {
        return eliminado;
    }

    public void marcarComoEliminado() {
        this.eliminado = true;
    }

    public boolean perteneceACriterio(Criterio criterio) {
        return criterio.filtarPorCriterio(this);
    }

    public LocalDateTime getFechaAcontecimiento() {
        return this.infoHecho.getFechaAcontecimiento();
    }
    public Categoria getCategoria() {
        return this.infoHecho.getCategoria();
    }
}
