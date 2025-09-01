package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos;

import lombok.Data;

@Data
public class TokenResponse {
    private boolean error;
    private String message;
    private DataResponse data;
}
