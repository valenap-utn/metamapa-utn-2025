package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class Hecho {
    @Setter @Getter private String id;
//    @Setter @Getter private ColeccionHecho coleccion;
    @Setter @Getter private String titulo;
    @Setter @Getter private String descripcion;
    @Setter @Getter private Categoria categoria;
    @Setter @Getter private Ubicacion ubicacion;
    @Setter @Getter private LocalDate fechaAcontecimiento;
    @Setter @Getter private LocalDate fechaCarga;
    @Setter @Getter private Origen origen;
    @Setter @Getter private boolean eliminado;
//    @Setter @Getter private boolean tieneContenidoMultimedia;
//    @Getter private Set<String> etiquetas;

    //Constructor de Hecho sin Coleccion asociada
    public Hecho(HechoValueObject infoHecho, Origen origen) {
        this.id = UUID.randomUUID().toString();
        this.titulo = infoHecho.getTitulo();
        this.descripcion = infoHecho.getDescripcion();
        this.categoria = infoHecho.getCategoria();
        this.ubicacion = infoHecho.getUbicacion();
        this.fechaAcontecimiento = infoHecho.getFechaAcontecimiento();
        this.origen = origen;
        this.fechaCarga = LocalDate.now();
        this.eliminado = false;
    }

    public String getID() { return id; }



    //Constructor de Hecho con Coleccion asignada (my error)
//    public Hecho(HechoValueObject infoHecho, Origen origen, ColeccionHecho coleccion) {
//        this.id = UUID.randomUUID().toString();
//        this.coleccion = coleccion;
//        this.titulo = infoHecho.getTitulo();
//        this.descripcion = infoHecho.getDescripcion();
//        this.categoria = infoHecho.getCategoria();
//        this.ubicacion = infoHecho.getUbicacion();
//        this.fechaAcontecimiento = infoHecho.getFechaAcontecimiento();
////        this.tieneContenidoMultimedia = false;
//        this.origen = origen;
//        this.fechaCarga = LocalDate.now();
//        this.eliminado = false;
////        this.etiquetas = new HashSet<>();
//    }

//    public void agregarEtiquetas(String ... etiquetas) {
//        this.etiquetas.addAll(List.of(etiquetas));
//    }
}