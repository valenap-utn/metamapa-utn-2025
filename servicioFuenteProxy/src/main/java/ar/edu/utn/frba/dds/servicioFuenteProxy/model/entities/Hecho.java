package ar.edu.utn.frba.dds.servicioFuenteProxy.model.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


public class Hecho {
    @Setter
    @Getter
    private HechoValueObject infoHecho;
    @Setter
    @Getter
    private LocalDate fechaCarga;
    @Setter
    @Getter
    private Origen origen;
    @Setter
    @Getter
    private boolean eliminado;
    @Setter
    @Getter
    private boolean tieneContenidoMultimedia;
    @Getter
    private Set<String> etiquetas;

    public Hecho(HechoValueObject infoHecho, Origen origen) {
        this.infoHecho = infoHecho;
        this.tieneContenidoMultimedia = false;
        this.origen = origen;
        this.fechaCarga = LocalDate.now();
        this.eliminado = false;
        this.etiquetas = new HashSet<>();
    }

    public void agregarEtiquetas(String ... etiquetas) {
        this.etiquetas.addAll(List.of(etiquetas));
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
