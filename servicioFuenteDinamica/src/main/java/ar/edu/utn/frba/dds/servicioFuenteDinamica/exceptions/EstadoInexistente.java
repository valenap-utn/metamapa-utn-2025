package ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions;

public class EstadoInexistente extends AppError {
  public EstadoInexistente(String s) {
    super(s, "Estado Inexistente");
  }
}
