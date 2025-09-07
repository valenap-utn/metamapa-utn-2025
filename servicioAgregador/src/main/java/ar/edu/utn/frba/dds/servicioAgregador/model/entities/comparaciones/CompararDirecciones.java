package ar.edu.utn.frba.dds.servicioAgregador.model.entities.comparaciones;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Documento;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Direccion;

public class CompararDirecciones extends ComparacionHechos{
  @Override
  protected Documento elementoAComparar(Hecho hechoExterno) {

    Direccion direccion = hechoExterno.getDireccion();
    if (direccion == null) {
      return Documento.ofStringSinNormalizar("");
    }
    String direccionStr = String.join(" ",
            normalizarCampo(direccion.getCiudad()),
            normalizarCampo(direccion.getLocalidad()),
            normalizarCampo(direccion.getMunicipio()),
            normalizarCampo(direccion.getProvincia())
    );

    return Documento.ofStringSinNormalizar(direccionStr.trim());
  }

    private String normalizarCampo(Object value) {
      return value == null ? "" : value.toString().toLowerCase().trim();
  }

}
