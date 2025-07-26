package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.TipoOrigen;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.ClientFuente;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.MapperFuenteClient;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.MapperFuenteDinamica;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.MapperFuenteEstatica;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.MapperFuenteProxy;
import org.springframework.stereotype.Component;

@Component
public class FactoryClientFuente {


  public static ClientFuente getClientPorOrigen(Origen origen) {
    return new ClientFuente(origen.getUrl(), mapperPorTipo(origen.getTipo()));
  }

  private static MapperFuenteClient mapperPorTipo(TipoOrigen tipo) {
    if(tipo == TipoOrigen.DATASET){
      return new MapperFuenteEstatica();
    } else if(tipo == TipoOrigen.PROXY) {
      return new MapperFuenteProxy();
    } else {
      return new MapperFuenteDinamica();
    }
  }


}
