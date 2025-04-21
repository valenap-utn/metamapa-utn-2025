package ar.edu.utn.frba.dds.domain.entities.colecciones;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.filtros.Criterio;
import ar.edu.utn.frba.dds.domain.filtros.Filtro;
import ar.edu.utn.frba.dds.domain.filtros.FiltroNoEstaEliminado;
import ar.edu.utn.frba.dds.domain.fuentes.Fuente;
import java.util.Arrays;
import java.util.List;
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


    public Coleccion(String titulo, String descripcion, Fuente fuente, Criterio criterio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuente = fuente;
        this.criterio = criterio;
    }


    public Set<Hecho> busquedaCon(Filtro ... filtros) {
        Set<Hecho> hechos = this.getHechos();
        return List.of(filtros).stream().reduce( this.getHechos(),
                (hechos, filtro) -> this.aplicarFiltro(hechos, filtro),
                (unosHechos, otrosHechos) -> this.aplicarHechos(unosHechos, otrosHechos)
        );
    }





    public Set<Hecho> getHechos() {
        FiltroNoEstaEliminado filtro = new FiltroNoEstaEliminado();
        return
                filtro.filtrarHechos(this.fuente.obtenerHechos());
    }

}

