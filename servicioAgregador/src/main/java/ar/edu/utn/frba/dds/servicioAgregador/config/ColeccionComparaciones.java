package ar.edu.utn.frba.dds.servicioAgregador.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "repocomp")
public class ColeccionComparaciones {
  private List<ComparacionDTO> comparaciones;
}
