package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class Ubicacion {
    @Column(name = "longitud")
    private float longitud;

    @Column(name = "latitud")
    private float latitud;

    public Ubicacion(float longitud, float latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }
}
