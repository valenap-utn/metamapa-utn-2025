package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos;

import lombok.Data;

@Data
public class DataResponse {
    private String access_token;
    private String token_type;
    /*"user": {
    "id": 1,
            "name": "DDSI User",
            "email": "ddsi@gmail.com",
            "email_verified_at": null,
            "created_at": "2025-05-06T21:22:14.000000Z",
            "updated_at": "2025-05-08T20:47:39.000000Z"
  }
  */
}
