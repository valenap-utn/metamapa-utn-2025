package ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions;

public class SinSolicitudValida extends AppError {
  public SinSolicitudValida(String mensaje) {
    super(mensaje, "Sin Solicitud Valida");
  }
}
