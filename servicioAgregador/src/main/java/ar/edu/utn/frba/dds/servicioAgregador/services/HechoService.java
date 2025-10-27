package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoCompleto;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.IHechoRepositoryJPA;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.specifications.HechoSpecification;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapHechoOutput;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class HechoService implements IHechoService{
  private final IHechoRepositoryJPA hechoRepository;
  private final MapHechoOutput mapHechoOutput;

  public HechoService(IHechoRepositoryJPA hechoRepository, MapHechoOutput mapHechoOutput) {
    this.hechoRepository = hechoRepository;
    this.mapHechoOutput = mapHechoOutput;
  }

  @Override
  @Transactional
  public ConjuntoHechoCompleto findAll(FiltroDTO filtroDTO) {
    Specification<Hecho> especificacionFiltros = HechoSpecification.filterBy(filtroDTO, null);
    List<Hecho> hechosDelSistema = this.hechoRepository.findAll(especificacionFiltros);
    if(filtroDTO.tieneFiltroUbicacion()) {
      hechosDelSistema = filtroDTO.filtrarPorUbicacion(hechosDelSistema);
    }
    return this.mapHechoOutput.toConjuntoHechoDTOOutput(hechosDelSistema);
  }

}
