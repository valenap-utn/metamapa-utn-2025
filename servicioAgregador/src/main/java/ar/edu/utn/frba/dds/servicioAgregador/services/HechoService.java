package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import org.springframework.stereotype.Service;

@Service
public class HechoService implements IHechoService{
  @Override
  public ConjuntoHechoCompleto findAll() {
    return null;
  }
}
