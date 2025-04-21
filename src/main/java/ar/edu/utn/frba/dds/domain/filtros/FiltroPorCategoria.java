package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Categoria;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

public class FiltroPorCategoria extends Filtro {
    private Categoria categoria;

    public FiltroPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean hechoCumple(Hecho hecho) {
        return hecho.getCategoria().esIgualA(this.categoria);
    }
}

