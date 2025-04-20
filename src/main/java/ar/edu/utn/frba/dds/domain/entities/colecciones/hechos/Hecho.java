package ar.edu.utn.frba.dds.domain.entities.colecciones.hechos;

import ar.edu.utn.frba.dds.domain.filtros.Criterio;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Hecho {
    private HechoValueObject infoHecho;
    private LocalDate fechaCarga;
    private boolean esCargaManual;
    private Origen origen;
    private boolean eliminado;
    private Set<String> etiquetas;

    public Hecho(HechoValueObject infoHecho, Origen origen) {
        this.infoHecho = infoHecho;
        this.origen = origen;
        this.fechaCarga = LocalDate.now();
        this.eliminado = false;
        this.etiquetas = new HashSet<>();
    }

    public boolean estaEliminado() {
        return eliminado;
    }

    public void marcarComoEliminado() {
        this.eliminado = true;
    }

    public void agregarEtiquetas(String ... etiquetas) {
        this.etiquetas.addAll(List.of(etiquetas));
    }

    public boolean perteneceACriterio(Criterio criterio) {
        return criterio.filtarPorCriterio(this);
    }

    public LocalDate getFechaAcontecimiento() {
        return this.infoHecho.getFechaAcontecimiento();
    }
    public Categoria getCategoria() {
        return this.infoHecho.getCategoria();
    }
}
