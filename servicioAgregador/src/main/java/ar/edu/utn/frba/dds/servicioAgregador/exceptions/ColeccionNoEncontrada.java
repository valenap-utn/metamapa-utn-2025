package ar.edu.utn.frba.dds.servicioAgregador.exceptions;

public class ColeccionNoEncontrada extends AppError {
  public ColeccionNoEncontrada(String mensaje) {
    super(mensaje, "Coleccion No encontrada");
  }
}
