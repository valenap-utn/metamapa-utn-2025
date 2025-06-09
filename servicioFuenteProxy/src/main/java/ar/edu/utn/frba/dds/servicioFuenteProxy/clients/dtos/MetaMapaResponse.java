package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos;

import lombok.Data;

import java.util.List;

@Data
public class MetaMapaResponse {
    private List<HechoInputDTO> hechos; // solo me importa este
}
