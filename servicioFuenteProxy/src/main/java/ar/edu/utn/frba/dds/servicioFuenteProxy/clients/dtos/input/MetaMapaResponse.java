package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input;

import lombok.Data;

import java.util.List;

@Data
public class MetaMapaResponse {
    private List<HechoDTOMetamapa> hechos; // solo me importa este
}
