package ar.edu.utn.frba.dds.servicioAgregador.model.entities.fuente;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

// tabla puente

@Getter
@Setter

@Entity
@Table(name = "coleccion_fuente")
public class ColeccionFuente {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column
    private UUID id;

    @NotNull
    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name = "coleccion_id", nullable = false)
    private Coleccion coleccion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoFuente tipo;

    @Column(nullable = false)
    private boolean habilitada = true;

    @Lob
    @Column(name = "parametros_json", nullable = false)
    private String parametrosJson = "{}"; // guarda config especifica (URL, archivo, etc.)

}
