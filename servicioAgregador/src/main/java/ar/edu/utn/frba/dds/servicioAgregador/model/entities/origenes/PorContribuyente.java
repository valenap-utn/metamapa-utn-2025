package ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;

public class PorContribuyente {
  Usuario usuario;

  PorContribuyente() {}
  PorContribuyente(Usuario usuario){
    this.usuario = usuario;
  }

  public boolean esIgualA(Origen origen) {
    return true;
  }
}
