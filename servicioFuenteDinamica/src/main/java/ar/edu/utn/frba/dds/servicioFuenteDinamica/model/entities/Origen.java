package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

public enum Origen {
  DATASET,
  PORCONTRIBUYENTE,
  CARGAMANUAL,
  PROXY;

  public boolean permiteSolicitud() {
    return true;
  }
}
