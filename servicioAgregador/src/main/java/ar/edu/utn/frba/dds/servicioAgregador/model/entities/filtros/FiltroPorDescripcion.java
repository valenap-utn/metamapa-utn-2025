package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public class FiltroPorDescripcion extends FiltroPorString{

  public FiltroPorDescripcion(String cadenaAcomparar) {
    super(cadenaAcomparar);
  }

  @Override
  protected String obtenerUnTipoString(Hecho unHecho) {
    return unHecho.getDescripcion();
  }
}
