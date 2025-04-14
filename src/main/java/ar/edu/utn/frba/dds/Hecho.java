package ar.edu.utn.frba.dds;

import domain.filtros.Criterio;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Hecho {
    private String titulo;
    private String descripcion;
    private String categoria;
    private Ubicacion ubicacion;
    private LocalDate fechaAcontecimiento;
    private LocalDate fechaCarga;
    private boolean esCargaManual;
    //private OrigenHecho origen; //TODO: ponerlo en clases como dijieron los ayudantes
    private boolean eliminado;

    public Hecho(String titulo, String descripcion, String categoria, Ubicacion ubicacion,
                 LocalDate fechaAcontecimiento) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.fechaAcontecimiento = fechaAcontecimiento;
       // this.origen = origen;
        this.fechaCarga = LocalDate.now();
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
}
