package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public class FiltroPorTitulo extends FiltroPorString{

  public FiltroPorTitulo(String cadenaAcomparar) {
    super(cadenaAcomparar);
  }

  @Override
  protected String obtenerUnTipoString(Hecho unHecho) {
    return unHecho.getTitulo();
  }
}
