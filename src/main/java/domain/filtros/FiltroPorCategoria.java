package domain.filtros;

import ar.edu.utn.frba.dds.Hecho;

public class FiltroPorCategoria implements Filtro {
    private String categoria;

    public FiltroPorCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean hechoCumple(Hecho hecho) {
        return hecho.getCategoria().equalsIgnoreCase(categoria);
    }
}

