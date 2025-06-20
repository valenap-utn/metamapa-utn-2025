package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class HechoService implements IHechoService{
  private IHechoRepository hechoRepository;
  public HechoService(IHechoRepository hechoRepository) {
    this.hechoRepository = hechoRepository;
  }

  @Override
  public ConjuntoHechoCompleto findAll() {
    Set<Hecho> hechosDelSistema = this.hechoRepository.findAll();
    return
  }
}
