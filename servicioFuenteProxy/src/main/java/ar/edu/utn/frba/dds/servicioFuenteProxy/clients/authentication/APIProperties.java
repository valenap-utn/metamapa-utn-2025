package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.authentication;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "api")

public class APIProperties {
    private String baseUrl;
    private String baseUrlMetaMapa;
    private Auth auth;

    @Data
    public static class Auth {
        private String email;
        private String password;
    }
}
