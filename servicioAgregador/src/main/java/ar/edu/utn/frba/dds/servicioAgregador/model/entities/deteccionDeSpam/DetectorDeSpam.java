package ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam;

public interface DetectorDeSpam {
  boolean esSpam(String texto);
}
