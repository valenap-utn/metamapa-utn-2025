package ar.edu.utn.frba.dds.domain.entities.colecciones;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.filtros.Criterio;
import ar.edu.utn.frba.dds.domain.filtros.Filtro;
import ar.edu.utn.frba.dds.domain.fuentes.Fuente;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Coleccion {
    private String titulo;
    private String descripcion;
    private Fuente fuente;
    private Criterio criterio;
    private Set<Hecho> hechosAsociados;

    public Coleccion(String titulo, String descripcion, Fuente fuente, Criterio criterio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuente = fuente;
        this.criterio = criterio;
        this.hechosAsociados = new HashSet<>();
    }


    public Set<Hecho> getHechosFiltradosSegun(Filtro unFiltro) {
        return this.hechosAsociados.stream().filter(
                hecho ->
                unFiltro.hechoCumple(hecho))
                .collect(Collectors.toSet());
    }

    public void cargarHechos() {
        this.hechosAsociados = fuente.obtenerHechos()
                .stream().filter( hecho -> !hecho.estaEliminado())
                .collect(Collectors.toSet());
    }


}

