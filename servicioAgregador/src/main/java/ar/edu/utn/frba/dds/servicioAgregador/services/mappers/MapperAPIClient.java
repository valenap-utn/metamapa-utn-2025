package ar.edu.utn.frba.dds.servicioAgregador.services.mappers;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTOEstatica;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTOProxy;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho.HechoBuilder;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.TipoOrigen;

public class MapperAPIClient {
  public Hecho toHechoFrom(HechoDTOEstatica hechoDTO) {
    return crearHechoBasico(hechoDTO)
            .origen(new Origen(TipoOrigen.DATASET))
            .build();
  }

  public Hecho toHechoFrom(HechoDTODinamica hechoDTODinamica) {
    return crearHechoConCategoria(hechoDTODinamica)
            .contenidoMultimedia(hechoDTODinamica.getContenidoMultimedia())
            .origen(new Origen(TipoOrigen.PORCONTRIBUYENTE))
            .build();
  }

  public Hecho toHechoFrom(HechoDTOProxy hechoDTOProxy) {
    return crearHechoBasico(hechoDTOProxy)
            .categoria(hechoDTOProxy.getCategoria())
            .ubicacion(new Ubicacion(hechoDTOProxy.getLongitud().floatValue(), hechoDTOProxy.getLatitud().floatValue()))
            .origen(new Origen(TipoOrigen.PROXY))
            .build();
  }

  private HechoBuilder crearHechoConCategoria(HechoDTO hechoDTO) {
    return this.crearHechoBasico(hechoDTO)
            .categoria(hechoDTO.getCategoria())
            .ubicacion(hechoDTO.getUbicacion());
  }


  private HechoBuilder crearHechoBasico(HechoDTO hechoDTO) {
    return Hecho.builder()
            .id(hechoDTO.getId())
            .titulo(hechoDTO.getTitulo())
            .descripcion(hechoDTO.getDescripcion())
            .fechaCarga(hechoDTO.getFechaCarga())
            .fechaAcontecimiento(hechoDTO.getFechaAcontecimiento());

  }
}
