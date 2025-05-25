package ar.edu.utn.frba.dds.servicioAgregador.model.entities;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.Filtro;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroNoEstaEliminado;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class Coleccion {
    private String id;
    private String titulo;
    private String descripcion;
    private final List<Fuente> fuentes;
    private final List<Filtro> criteriosDePertenencia;


    public Coleccion(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuentes = new ArrayList<>();
        this.criteriosDePertenencia = new ArrayList<>();
        this.criteriosDePertenencia.add(new FiltroNoEstaEliminado());
    }

    public void agregarCriterios(Filtro ... filtros) {
        this.criteriosDePertenencia.addAll(List.of(filtros));
    }

    public Set<Hecho> getHechos() {
        return this.fuentes.stream().flatMap(this::getHechosPorFuente).collect(Collectors.toSet());

    }

    private Stream<Hecho> getHechosPorFuente(Fuente fuente) {
        return fuente.getHechos().stream().filter(this::cumpleTodosLosCriterios);
    }

    public void agregarFuentes(Collection<Fuente> fuentes) {
        this.fuentes.addAll(fuentes);
    }


    private boolean cumpleTodosLosCriterios(Hecho hecho) {
        return this.criteriosDePertenencia.stream().allMatch(criterio -> criterio.hechoCumple(hecho));
    }

}

