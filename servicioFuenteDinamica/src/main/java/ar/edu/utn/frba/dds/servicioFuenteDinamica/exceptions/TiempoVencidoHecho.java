package ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions;

public class TiempoVencidoHecho extends AppError {
  public TiempoVencidoHecho(String s) {
    super(s, "Tiempo Vencido Hecho");
  }
}
