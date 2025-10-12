package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

public class ColeccionConDatosErroneos extends AppError {
  public ColeccionConDatosErroneos(String s) {
    super(s, "Coleccion de datos erroneos");
  }
}
