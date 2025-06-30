package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class APIResponse {
    @Getter private List<HechoInputDTO> data; // solo me importa este

}
