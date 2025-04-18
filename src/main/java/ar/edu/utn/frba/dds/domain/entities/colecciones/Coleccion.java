package ar.edu.utn.frba.dds.domain.entities.colecciones;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.filtros.Criterio;
import ar.edu.utn.frba.dds.domain.fuentes.Fuente;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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
    }



}

