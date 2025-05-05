package ar.edu.utn.frba.dds.domain.entities.colecciones;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.filtros.Criterio;
import ar.edu.utn.frba.dds.domain.filtros.Filtro;
import ar.edu.utn.frba.dds.domain.fuentes.Fuente;
import ar.edu.utn.frba.dds.utils.FoldlDeFunciones;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private Fuente fuente;
    private List<Filtro> criteriosDePertenencia;


    public Coleccion(String titulo, String descripcion, Fuente fuente) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuente = fuente;
        this.criteriosDePertenencia = new ArrayList<>();
    }

    /* Posiblemente se elimine
    public Set<Hecho> busquedaCon(Filtro ... filtros) {
        return FoldlDeFunciones.foldl(this.getHechos(), List.of(filtros));
    }
    */
    public void agregarCriterios(Filtro ... filtros) {
        this.criteriosDePertenencia.addAll(List.of(filtros));
    }

    public Set<Hecho> getHechos() {
        return this.fuente.obtenerHechos().stream()
                .filter(this::cumpleTodosLosCriterios)
                .collect(Collectors.toSet());
    }

    private boolean cumpleTodosLosCriterios(Hecho hecho) {
        return this.criteriosDePertenencia.stream().allMatch(Filtro::hechoCumple)
    }

}

