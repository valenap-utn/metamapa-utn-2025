package ar.edu.utn.frba.dds;

import domain.filtros.Criterio;
import domain.fuentes.Fuente;

import java.util.HashSet;
import java.util.Set;

public class Coleccion {
    private String titulo;
    private String descripcion;
    private Fuente fuente;
    private Criterio criterio;

    public Coleccion(String titulo, String descripcion, Fuente fuente, Criterio criterio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuente = fuente;
        this.criterio = criterio;
    }

    public Set<Hecho> hechos() {
        Set<Hecho> todos = fuente.obtenerHechos();
        Set<Hecho> filtrados = new HashSet<>();
        for (Hecho h : todos) {
            if (!h.estaEliminado() && h.perteneceACriterio(criterio)) {
                filtrados.add(h);
            }
        }
        return filtrados;
    }

}
