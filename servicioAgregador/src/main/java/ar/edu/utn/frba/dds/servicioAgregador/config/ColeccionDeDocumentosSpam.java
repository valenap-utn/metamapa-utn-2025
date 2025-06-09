package ar.edu.utn.frba.dds.servicioAgregador.config;

import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "repodocumento")
public class ColeccionDeDocumentosSpam {
  private List<String> documentos;
}
