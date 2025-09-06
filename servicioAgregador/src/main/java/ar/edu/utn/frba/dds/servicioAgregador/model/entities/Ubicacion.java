package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class Ubicacion {
    @Column(name = "longitud")
    private Float longitud;
    @Column(name = "latitud")
    private Float latitud;
    @OneToOne
    @JoinColumn(name="direccion_id")
    private Direccion direccion;

    public Ubicacion(Float longitud, Float latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }
}
