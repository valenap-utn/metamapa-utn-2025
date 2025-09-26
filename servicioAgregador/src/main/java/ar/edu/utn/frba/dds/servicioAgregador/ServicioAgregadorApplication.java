package ar.edu.utn.frba.dds.servicioAgregador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
@PropertySource("classpath:coleccionDocumentos.properties")
@PropertySource("classpath:metodosDeComparacion.properties")
@ConfigurationPropertiesScan
public class ServicioAgregadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioAgregadorApplication.class, args);
	}

}
