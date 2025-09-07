package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapHechoOutput;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HechoService implements IHechoService{
  private final IHechoRepository hechoRepository;
  private final MapHechoOutput mapHechoOutput;

  public HechoService(IHechoRepository hechoRepository, MapHechoOutput mapHechoOutput) {
    this.hechoRepository = hechoRepository;
    this.mapHechoOutput = mapHechoOutput;
  }

  @Override
  public ConjuntoHechoCompleto findAll(FiltroDTO filtroDTO) {
    List<Hecho> hechosDelSistema = this.hechoRepository.findAll();
    return this.mapHechoOutput.toConjuntoHechoDTOOutput(hechosDelSistema);
  }
}
