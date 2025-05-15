package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Ubicacion {
    private float longitud;
    private float latitud;

    public Ubicacion(float longitud, float latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }
}
