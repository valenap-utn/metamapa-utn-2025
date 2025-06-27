package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.config.ColeccionDeDocumentosSpam;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.DetectorDeSpam;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.DetectorDeSpamBasico;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import org.springframework.stereotype.Component;

@Component
public class FactoryDetectorDeSpam {
  private final ColeccionDeDocumentosSpam coleccionDeDocumentosSpam;
  private Double nivelDeImportanciaMinimo = 0.5;
  public FactoryDetectorDeSpam(ColeccionDeDocumentosSpam coleccionDeDocumentosSpam) {
    this.coleccionDeDocumentosSpam = coleccionDeDocumentosSpam;
  }

  public DetectorDeSpam crearDetectorDeSpamBasico() {
    TFIDFCalculadoraPalabras calculadoraTFIDF = new TFIDFCalculadoraPalabras(this.coleccionDeDocumentosSpam);
    return new DetectorDeSpamBasico(calculadoraTFIDF, this.nivelDeImportanciaMinimo);
  }


}
