package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IFuenteEstaticaDinamicaRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IFuenteProxyRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapColeccionOutput;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapHechoOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class HechoService implements IHechoService{
  private IHechoRepository hechoRepository;
  private MapHechoOutput mapHechoOutput;
  private final IFuenteEstaticaDinamicaRepository fuenteRepository;
  private final IFuenteProxyRepository fuenteProxyRepository;
  public HechoService(IHechoRepository hechoRepository, IFuenteEstaticaDinamicaRepository fuenteRepository, IFuenteProxyRepository fuenteProxyRepository) {
    this.hechoRepository = hechoRepository;
    this.fuenteRepository = fuenteRepository;
    this.fuenteProxyRepository = fuenteProxyRepository;
  }

  @Override
  public ConjuntoHechoCompleto findAll(FiltroDTO filtroDTO) {
    List<Hecho> hechosDelSistema = new ArrayList<>();
    List<Fuente> fuentes = new ArrayList<>();
    fuentes.addAll( this.fuenteRepository.findAll());
    fuentes.addAll(this.fuenteProxyRepository.findAll());

    fuentes.stream().forEach( fuente -> hechosDelSistema.addAll(this.hechoRepository.findByOrigenWithFiltros(fuente.getOrigen(), filtroDTO)));

    return this.mapHechoOutput.toConjuntoHechoDTOOutput(hechosDelSistema);
  }
}
