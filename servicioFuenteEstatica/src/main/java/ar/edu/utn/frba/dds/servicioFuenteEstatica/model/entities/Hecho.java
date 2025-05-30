package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Builder
public class Hecho {
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Ubicacion ubicacion;
    private LocalDate fechaAcontecimiento;
    private LocalDate fechaCarga;

    public Hecho() {

    }


    public LocalDate getFechaAcontecimiento() {
        return this.infoHecho.getFechaAcontecimiento();
    }
    public Categoria getCategoria() {
        return this.infoHecho.getCategoria();
    }

    public Ubicacion getUbicacion() {
        return this.infoHecho.getUbicacion();
    }

    public String getDescripcion() {
        return this.infoHecho.getDescripcion();
    }

    public String getTitulo() {
        return this.infoHecho.getTitulo();
    }
}
