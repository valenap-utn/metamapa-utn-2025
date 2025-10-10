package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

public class ColeccionYaEliminada extends AppError {
  public ColeccionYaEliminada(String s) {
    super(s, "Coleccion Ya Eliminada");
  }
}
