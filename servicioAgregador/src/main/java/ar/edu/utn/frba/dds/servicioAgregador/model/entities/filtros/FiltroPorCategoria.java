package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.CriterioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("CATEGORIA")
public class FiltroPorCategoria extends Filtro {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "id", name = "categoria_id")
    private Categoria categoria;

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

