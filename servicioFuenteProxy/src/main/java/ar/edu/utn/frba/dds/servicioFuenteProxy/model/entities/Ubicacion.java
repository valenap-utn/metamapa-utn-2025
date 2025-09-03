package ar.edu.utn.frba.dds.servicioFuenteProxy.model.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Ubicacion {
    private Float longitud;
    private Float latitud;

    public Ubicacion(float longitud, float latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }
}
