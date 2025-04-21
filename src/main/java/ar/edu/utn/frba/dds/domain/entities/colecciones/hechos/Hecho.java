package ar.edu.utn.frba.dds.domain.entities.colecciones.hechos;

import ar.edu.utn.frba.dds.domain.filtros.Criterio;
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
    @Getter
    private Set<String> etiquetas;

    public Hecho(HechoValueObject infoHecho, Origen origen) {
        this.infoHecho = infoHecho;
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
}
