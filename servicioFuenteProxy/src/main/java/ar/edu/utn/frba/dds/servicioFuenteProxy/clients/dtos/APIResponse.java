package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos;

import lombok.Data;

import java.util.List;

@Data
public class APIResponse {
    private List<HechoInputDTO> data; // solo me importa este

}
