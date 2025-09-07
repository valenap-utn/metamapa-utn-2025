package ar.edu.utn.frba.dds.servicioAgregador.model.entities.comparaciones;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Documento;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public class CompararDirecciones extends ComparacionHechos{
  @Override
  protected Documento elementoAComparar(Hecho hechoExterno) {
    //TODO
    return Documento.ofStringSinNormalizar("");//hechoExterno.getDireccion());
  }
}
