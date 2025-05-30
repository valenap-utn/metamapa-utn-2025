package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoValueObject;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@AllArgsConstructor
public class Hecho {
    @Getter
    @Setter
    private Long id;
    @Setter
    @Getter
    private String titulo;
    @Setter
    @Getter
    private String descripcion;
    @Setter
    @Getter
    private Categoria categoria;
    @Setter
    @Getter
    private Ubicacion ubicacion;
    @Setter
    @Getter
    private LocalDate fechaAcontecimiento;
    @Setter
    @Getter
    private LocalDate fechaCarga;
    @Setter
    @Getter
    private Origen origen;
    @Setter
    @Getter
    private boolean eliminado;
    @Setter
    @Getter
    private ContenidoMultimedia contenidoMultimedia;
    @Getter
    private final Set<String> etiquetas = new HashSet<>();
    public Hecho() {
        this.eliminado = false;
    }

    public Hecho(HechoValueObject infoHecho, Origen origen) {
        this.titulo = infoHecho.getTitulo();
        this.descripcion = infoHecho.getDescripcion();
        this.categoria = infoHecho.getCategoria();
        this.ubicacion = infoHecho.getUbicacion();
        this.fechaAcontecimiento = infoHecho.getFechaAcontecimiento();
        this.origen = origen;
        this.fechaCarga = LocalDate.now();
        this.eliminado = false;
    }

    public void agregarEtiquetas(String ... etiquetas) {
        this.etiquetas.addAll(List.of(etiquetas));
    }

}
