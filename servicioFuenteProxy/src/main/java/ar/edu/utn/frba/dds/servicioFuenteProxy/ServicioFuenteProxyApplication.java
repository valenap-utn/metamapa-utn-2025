package ar.edu.utn.frba.dds.servicioFuenteProxy;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.authentication.APIProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/application/configuracion.properties")
@ConfigurationPropertiesScan
public class ServicioFuenteProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioFuenteProxyApplication.class, args);
	}

}
