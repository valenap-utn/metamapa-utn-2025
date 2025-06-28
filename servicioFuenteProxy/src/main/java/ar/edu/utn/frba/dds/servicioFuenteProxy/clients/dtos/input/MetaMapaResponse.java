package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input;

import lombok.Data;

import java.util.List;

@Data
public class MetaMapaResponse {
    private List<HechoInputDTO> hechos; // solo me importa este
}
