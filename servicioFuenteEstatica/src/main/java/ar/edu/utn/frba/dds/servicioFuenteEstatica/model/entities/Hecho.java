package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hecho")
@AllArgsConstructor
@NoArgsConstructor
public class Hecho {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter private Long id;

    @Column(nullable = false, name = "titulo")
    @Setter @Getter private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    @Setter @Getter private String descripcion;

    @ManyToOne @JoinColumn(referencedColumnName = "id", nullable = false, name = "categoria_id")
    @Setter @Getter private Categoria categoria;

    @Embedded
    @Setter @Getter private Ubicacion ubicacion;

    @Column(name = "fecha_acontecimiento")
    @Setter @Getter private LocalDateTime fechaAcontecimiento;

    @Column(name = "fecha_carga")
    @Setter @Getter private LocalDateTime fechaCarga;

    @Enumerated(EnumType.STRING)
    @Column(name = "origen")
    @Setter @Getter private Origen origen;

    @Column(name = "eliminado")
    @Setter @Getter private Boolean eliminado;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "usuario_id")
    @Setter @Getter private Usuario usuario;


    public Hecho(HechoValueObject infoHecho, Origen origen) {
        this.titulo = infoHecho.getTitulo();
        this.descripcion = infoHecho.getDescripcion();
        this.categoria = infoHecho.getCategoria();
        this.ubicacion = infoHecho.getUbicacion();
        this.fechaAcontecimiento = infoHecho.getFechaAcontecimiento();
        this.origen = origen;
        this.fechaCarga = LocalDateTime.now();
        this.eliminado = false;
    }

    public Long getIdUsuario() {
        return usuario.getId();
    }

}