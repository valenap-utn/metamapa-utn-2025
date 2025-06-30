package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.EstadoHecho;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


public class Hecho {
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
    private MultipartFile contenidoMultimedia;
    @Getter
    private Set<String> etiquetas;
    @Getter
    @Setter
    private EstadoHecho estadoHecho;
    @Setter
    private String comentarioRevision;
    @Setter
    @Getter
    private String contenido;
    @Setter
    @Getter
    private Long id;
    @Setter
    @Getter
    private boolean esAnonimo;

    public Hecho() {
        this.fechaCarga = LocalDate.now();
        this.eliminado = false;
        this.etiquetas = new HashSet<>();
    }

    public void agregarEtiquetas(String ... etiquetas) {
        this.etiquetas.addAll(List.of(etiquetas));
    }

}
