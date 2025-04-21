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
    private Criterio criterio;


    public Coleccion(String titulo, String descripcion, Fuente fuente, Criterio criterio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuente = fuente;
        this.criterio = criterio;
    }


    public Set<Hecho> busquedaCon(Filtro ... filtros) {
        return FoldlDeFunciones.foldl(this.getHechos(), List.of(filtros));
    }


    public Set<Hecho> getHechos() {
        return this.criterio.getHechosFiltrados(this.fuente.obtenerHechos());
    }

}

