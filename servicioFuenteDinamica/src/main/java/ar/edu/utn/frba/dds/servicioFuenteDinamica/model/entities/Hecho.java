package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoValueObject;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.enums.EstadoHecho;
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
    @Getter
    @Setter
    private EstadoHecho estadoHecho;
    @Setter
    private String comentarioRevision;
    @Setter
    @Getter
    private String contenido;
    @Setter
    @Getter
    private Long id;

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
