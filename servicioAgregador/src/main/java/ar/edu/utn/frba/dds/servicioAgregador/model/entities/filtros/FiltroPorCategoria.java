package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public class FiltroPorCategoria implements Filtro {
    private final Categoria categoria;

    public FiltroPorCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean hechoCumple(Hecho hecho) {
        return hecho.getCategoria().esIgualA(this.categoria);
    }

    @Override
    public CriterioDTO toCriterioDTO() {
        CriterioDTO criterioDTO = new CriterioDTO();
        criterioDTO.setCategoria(this.categoria.getNombre());
        criterioDTO.setTipo("CATEGORIA");
        return criterioDTO;
    }
}

